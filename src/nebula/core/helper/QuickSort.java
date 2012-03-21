package nebula.core.helper;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;
/**
 * Class used to sort the Vector2fList
 * @author gaubert
 *
 */
public class QuickSort {

	private ArrayList<Vector2f> numbers;
	private int number;

	public QuickSort(){
	}
	
	public void sortByX(ArrayList<Vector2f> values) {
		// Check for empty or null array
		if (values ==null || values.size()==0){
			return;
		}

		this.numbers = values;
		number = values.size();
		quicksortByX(0, number - 1);
	}
	
	public void sortByY(ArrayList<Vector2f> values) {
		// Check for empty or null array
		if (values ==null || values.size()==0){
			return;
		}

		this.numbers = values;
		number = values.size();
		quicksortByY(0, number - 1);
	}

	private void quicksortByX(int low, int high) {
		int i = low, j = high;
		// Get the pivot element from the middle of the list
		Vector2f pivot = numbers.get(low + (high-low)/2);

		// Divide into two lists
		while (i <= j) {
			// If the current value from the left list is smaller then the pivot
			// element then get the next element from the left list
			while (numbers.get(i).x < pivot.x) {
				i++;
			}
			// If the current value from the right list is larger then the pivot
			// element then get the next element from the right list
			while (numbers.get(j).x > pivot.x) {
				j--;
			}
			// If we have found a values in the left list which is larger then
			// the pivot element and if we have found a value in the right list
			// which is smaller then the pivot element then we exchange the
			// values.
			// As we are done we can increase i and j
			if (i <= j) {
				exchange(i, j);
				i++;
				j--;
			}
		}
		// Recursion
		if (low < j)
			quicksortByX(low, j);
		if (i < high)
			quicksortByX(i, high);
	}

	private void quicksortByY(int low, int high) {
		int i = low, j = high;
		// Get the pivot element from the middle of the list
		Vector2f pivot = numbers.get(low + (high-low)/2);

		// Divide into two lists
		while (i <= j) {
			// If the current value from the left list is smaller then the pivot
			// element then get the next element from the left list
			while (numbers.get(i).y < pivot.y) {
				i++;
			}
			// If the current value from the right list is larger then the pivot
			// element then get the next element from the right list
			while (numbers.get(j).y > pivot.y) {
				j--;
			}
			// If we have found a values in the left list which is larger then
			// the pivot element and if we have found a value in the right list
			// which is smaller then the pivot element then we exchange the
			// values.
			// As we are done we can increase i and j
			if (i <= j) {
				exchange(i, j);
				i++;
				j--;
			}
		}
		// Recursion
		if (low < j)
			quicksortByX(low, j);
		if (i < high)
			quicksortByX(i, high);
	}
	
	private void exchange(int i, int j) {
		Vector2f temp = numbers.get(i);
		numbers.set(i, numbers.get(j));
		numbers.set(j, temp);
	}
}