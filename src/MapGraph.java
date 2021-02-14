import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MapGraph {
	private Hashtable<String, Location> places = new Hashtable<String, Location>();;
	private static final int numConnections = 3;

	public MapGraph() {
        String filePath = System.getProperty("user.dir") + File.separator + "resources\\Locations.xlsx";
        readFile(filePath);
		setupConnections();
    }

    // must be in resources folder to be found
    public MapGraph(String fileName) {
        String filePath = System.getProperty("user.dir") + File.separator + "resources\\" + fileName;
        readFile(filePath);
		setupConnections();
    }

	//Excel file needs three columns, 
    //the first column with the name in it, 
    //second column with the latitude
    //third column with the longitude
    //default sheet name
	private void readFile(String locPath) {
        try {
            File file = new File(locPath);
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheet("Sheet1");
            for(Row row : sheet) {
                String locName = null;
                double[] coords = new double[2];
                for(Cell cell : row) {
                    if(cell.getColumnIndex() == 0) locName = cell.getStringCellValue();
                    else if(cell.getColumnIndex() == 1) coords[0] = cell.getNumericCellValue();
                    else if(cell.getColumnIndex() == 2) coords[1] = cell.getNumericCellValue();
                }
                places.put(locName,new Location(coords[0], coords[1]));
            }
            fis.close();
        }
        catch(Exception e) {
            System.out.println("error occured: "+e.toString());
        }
    }

	private void setupConnections() {
        if(places.size() < numConnections) {
            System.out.println("Add more locations or decrease the number of connections between places");
            return;
        }
        for(String locationName : places.keySet()) {
            double[] minDistCosts = new double[numConnections];
            double[] minTimeCosts = new double[numConnections];
            String[] minLocations = new String[numConnections];
			Location location1 = places.get(locationName);
            for(int i=0; i<minDistCosts.length;i++) {
                minDistCosts[i] = Double.MAX_VALUE;
                minTimeCosts[i] = Double.MAX_VALUE;
                minLocations[i] = null;
            }
            for(String locationName2 : places.keySet()) {
                Location location2 = places.get(locationName2);

                if(!locationName.equals(locationName2)) {
                    double distCost = location1.distanceCalculator(location2.getCoords());
                    double timeCost = location1.timeCalculator(distCost);

                    for(int i=0;i<minDistCosts.length;i++) {
                        if(distCost < minDistCosts[i]) {
                            minDistCosts[i] = distCost;
                            minTimeCosts[i] = timeCost;
                            minLocations[i] = locationName2;
                        }
                    }
                }
            }
			for(int i=0;i<minDistCosts.length;i++) {
				location1.addEdge(minLocations[i], minDistCosts[i], minTimeCosts[i]);
			}
        }
    }

	private class Location {
		private ArrayList<Edge> neighbors;
		private double[] coords;
    

		public Location(double lat, double lon) {
			neighbors = new ArrayList<Edge>();
			coords = new double[2];
			coords[0] = lat;
			coords[1] = lon;
		}
	
		public void addEdge(String name, double dc, double tc) {
			Location otherNode = places.get(name);
			neighbors.add(new Edge(otherNode, dc, tc));
		}
		
		public double distanceCalculator(double[] Loc2) {
			//distance using latitude and longitude
			//Assumes latitude is stored in first slot in double[] ex Loc2 = [Latitude,Longitude]
			double distLat = Math.toRadians(Loc2[0] - coords[0]);
			double distLon = Math.toRadians(Loc2[1] - coords[1]);
			
			double lat1Radians = Math.toRadians(coords[0]);
			double lat2Radians = Math.toRadians(Loc2[0]);
			
			double haversine = Math.pow(Math.sin(distLat / 2), 2) +
							   Math.pow(Math.sin(distLon / 2), 2) *
							   Math.cos(lat1Radians) *
							   Math.cos(lat2Radians); //haversine formula used from coordinates
			
			double radius = 6371; //radius of earth
			double distanceKM = 2 * Math.asin(Math.sqrt(haversine)) * radius; //second part of haversine formula
			double distanceInMiles = distanceKM / 1.609344;
			return distanceInMiles;
		}
		
		public int timeCalculator(double distance) {
			int averageSpeed = 0;
			if(distance <= 99) averageSpeed = 40;
			else if(distance >= 51 && distance <= 99) averageSpeed = 50;
			else averageSpeed = 60;
			int estimatedTime = (int) (distance / averageSpeed);
			return estimatedTime;
		}
	
		public double[] getCoords() {
			return coords;
		}
	}
	
	private class Edge {
		private Location otherNode;
		private double distCost;
		private double timeCost;
		
		public Edge(Location n, double dc, double tc){
			otherNode = n;
			distCost = dc;
			timeCost = tc;
		}
	}
	

	public boolean addNode(double lat, double lon, String name) {
		places.put(name, new Location(lat,lon));
		return true;
	}
	
	public boolean addEdge(String name1, String name2, double dc, double tc) {
		if (!places.containsKey(name1) && !places.containsKey(name2)) return false;
		places.get(name1).addEdge(name2, dc, tc);
	    return true;
	}
}
