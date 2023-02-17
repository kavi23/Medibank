package au.com.medibank;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileSorting {

	/**
	 * Main Method
	 * @param args
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException {

		//Folder Directory
		String dir = "data";
		ArrayList<String> fileNameList = new ArrayList<String>();

		//Walk through Folder directory to Get list of filenames
		fileNameList = getFileNameList(dir);

		System.out.println("File Name Array Size "+ fileNameList.size());
		//printFileNames(fileNameList);

		Map<String,Integer> hshmap = new HashMap<String, Integer>();
		//Aggregate the Filenames Occurrence into a hashmap
		for (String str : fileNameList) 
		{ 
			if (hshmap.keySet().contains(str)) // if already exists then update count. 
				hshmap.put(str, hshmap.get(str) + 1); 
			else
				hshmap.put(str, 1); // else insert it in the map.
		}
		//System.out.println(hshmap);
		//Sort Map based on HashMap Value in Descending order and occurrences not equal to 1
		LinkedHashMap<String, Integer> fileNameSortedMap = sortMapByValue(hshmap);
		printFileNames(fileNameSortedMap);

	}
	/**
	 * 
	 * @param dir
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<String> getFileNameList(String dir) throws IOException {
		ArrayList<String> fileList = new ArrayList<String>();
		Files.walkFileTree(Paths.get(dir), new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
				if (!Files.isDirectory(file)) {
					fileList.add(file.getFileName().toString());
				}
				return FileVisitResult.CONTINUE;
			}
		});
		return fileList;
	}
	/**
	 * Sort Map based on HashMap Value in Descending order and occurrences not equal to 1
	 * https://howtodoinjava.com/java/sort/java-sort-map-by-values/
	 * @param unSortedMap
	 */
	private static LinkedHashMap<String, Integer> sortMapByValue(Map<String, Integer> unSortedMap) 
	{
		//Map<String, Integer> unSortedMap = getUnSortedMap();

		//System.out.println("Unsorted Map : " + unSortedMap); 
		/**LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
	    unSortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
	        .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));     
	    System.out.println("Sorted Map   : " + sortedMap);**/ 
		
		LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
		unSortedMap
		.entrySet()
		.stream()
		.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		.forEachOrdered(x -> { if(x.getValue() != 1) {
			reverseSortedMap.put(x.getKey(), x.getValue());
			}
		});     
		//System.out.println("Reverse Sorted Map   : " + reverseSortedMap);
		return reverseSortedMap;
	}
	/**
	 * Prints Key and Values of the HashMap
	 * @param sortMapByValue
	 */
	public static void printFileNames(LinkedHashMap<String, Integer> sortMapByValue){
		for (String fileName :  sortMapByValue.keySet()) {
			System.out.println(fileName + " " + sortMapByValue.get(fileName));
		}
	} 
}
