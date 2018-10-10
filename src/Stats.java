/* Stats.java

   COMP 2140 SECTION A01
   INSTRUCTOR   Cameron(A01)
   ASSIGNMENT   1
   @author      Luke Morrow, 7787696
   @version     2018-10-08

    PURPOSE: This class runs 100(default) tests on 4 kinds of sorts
    (insertion, merge, quick, and a hybrid quick/insertion).
    And times their sorts then prints the mean, standard deviation,
    and the z-test between each of the sorts.
 */
import java.lang.Math;

public class Stats {

    private static final int BREAKPOINT = 50;
    private static final int NUM_TESTS = 100;
    private static final int TEST_SIZE = 10000;
    private static final int NUM_RANDOM_SWAPS = 2500;

    /*
     * Creates a mean based from a passed array
     */
    public static double mean(long data[]) {
        double sum = 0;
        for (int i = 0; i < data.length; i++)
            sum += data[i];
        return sum / (double) data.length;
    }

    /*
     * Creates a standard deviation from a passed array
     */
    public static double standardDeviation(long data[]) {
        double mean = mean(data);

        double summation = 0;
        for (int i = 0; i < data.length; i++) {
            summation += Math.pow(data[i] - mean, 2);
        }
        return Math.sqrt(1 / (double) data.length * summation);
    }

    /*
     * Return a z-statistic, difference, reported in standard deviations
     */
    public static double zTest(long left[], long right[]) {
        return (mean(left) - mean(right)) /
                Math.sqrt(Math.pow(standardDeviation(left), 2) + Math.pow(standardDeviation(right), 2));
    }



    public static void main(String[] args)
    {
        testAndBenchmark(NUM_TESTS, TEST_SIZE, NUM_RANDOM_SWAPS);
    }//END MAIN

    //      SORTING WORK
    //A NON- RECURSIVE INSERTION SORT ALGORITHM
    public static void insertionSort(int[] nums) {
        insertionSort(nums, 0, nums.length - 1);
    }

    private static void insertionSort(int[] nums, int start, int end) {
        int itemValue;
        int sortingIndex;

        for (int i = start; i < end; i++) {
            itemValue = nums[i];//the value we are currently looking to sort
            sortingIndex = i - 1;//our search index for the sorted portion

            //while index is in the part of the array we are sorting and
            //current item is greater than our value keep looking.
            while (sortingIndex >= start && nums[sortingIndex] > itemValue) {
                nums[sortingIndex + 1] = nums[sortingIndex];//moves items to make way for the new element
                sortingIndex--;
            }//END WHILE

            nums[sortingIndex + 1] = itemValue;//add item where while loop stopped

        }//END FOR
    }//END INSERTIONSORT


    //      RECURSIVE WORK
    //A RECURSIVE MERGE SORT ALGORITHM
    public static void mergeSort(int[] nums) {
        int[] temp = new int[nums.length];
        mergeSort(nums, 0, nums.length - 1, temp);
    }

    private static void mergeSort(int[] nums, int start, int end, int[] temp) {
        int mid;

        if (1 < (end - start))//only run if there is more then 1 item in a list
        {
            mid = start + (end - start) / 2;// define the middle of the array

            mergeSort(nums, start, mid, temp);//recursively sort the lower half
            mergeSort(nums, mid, end, temp);//recursively sort the upper half

            merge(nums, start, mid, end, temp);//merge those two halves into one sorted list

        }//END IF
    }//END MERGESORT

    private static void merge(int[] nums, int start, int mid, int end, int[] temp) {
        int currL = start;
        int currR = mid;
        int currT;

        for (currT = start; currT < end; currT++) {
            /*if('L' is in its proper range, and if ('R' has run out of its range
            or 'a[R]' is bigger than 'a[L]')) */
            if (currL < mid && (currR >= end || nums[currL] < nums[currR])) {
                temp[currT] = nums[currL];//put 'a[L]' in next
                currL++;
            }//END IF
            else {
                temp[currT] = nums[currR];
                currR++;
            }//END ELSE
        }//END FOR

        for(currT = start; currT<end; currT++)
        { nums[currT]=temp[currT];}
        //^deep copy the temp array to the nums array

    }//END MERGE


    //A RECURSIVE QUICK SORT ALGORITHM
    /*A driver method to ensure intuitive use for the user
        >nums is the main array
     */
    public static void quicksort(int[] nums) {
        quicksort(nums, 0, nums.length);
    }

    /*The main body of the quicksort algorithm, this method will
        recursively partition an ever decreasing(in size) part of the array
        to approximately sorted halves.

        -uses partition to approximately sort the halves
        -uses medianOf3 to find a pivot that is not a worst case scenario.

         >nums is the main array
         >start is the start of the part being sorted
         >end is the end of the part being sorted
     */
    private static void quicksort(int[] nums, int start, int end) {
        if (end - start > 1)//run if there are at least two items to compare
        {
            medianOf3(nums, start, end - 1);//choose a non-terrible pivot
            int middle = partition(nums, start, end - 1);//approx. sort to get the pivot in the middle

            quicksort(nums, start, middle);//rec. sort the first half
            quicksort(nums, middle, end - 1);//rec. sort the second half
        }//END IF
    }//END QUICKSORT

    /*This method will approximately sort the two halves
         of the part of the array given around an assumed pivot in the
         first position (should have been placed there by mediaOf3)

         -uses swap for a simple swapping of two variables

         >nums is the main array
         >start is the start of the part being approx. sorted
         >end is the end of the part being approx. sorted
     */
    private static int partition(int[] nums, int start, int end) {
        int bigStart = start + 1;//+1 because the pivot is in the first position
        int curr = start + 1;//+1 because the pivot is in the first position
        while (curr <= end) {
            if (nums[curr] < nums[start])//current item is smaller than pivot
            {
                swap(nums, bigStart, curr);
                bigStart++;//make the small side bigger
            }
            curr++;
        }
        swap(nums, start, bigStart - 1);//move pivot to the middle
        return bigStart - 1;//returns the current position of the pivot
    }

    /*This method will grab the first, middle, and last item of the
         array and compare to find the /median of 3/

         -uses swap for a simple swapping of two variables

         >nums is the main array
         >start is the start of the part being viewed
         >end is the end of the part being viewed
     */
    private static void medianOf3(int[] nums, int start, int end) {
        int middle = start + (end - start) / 2;

        if (nums[start] <= nums[middle] && nums[middle] < nums[end] ||
                nums[end] <= nums[middle] && nums[middle] < nums[start])
            swap(nums, start, middle);    //if middle is the median value

        else if (nums[middle] <= nums[end] && nums[end] < nums[start] ||
                nums[start] <= nums[end] && nums[middle] < nums[end])
            swap(nums, start, end);       //if end is the median value
        //base case is that 0 is the median value
    }

    /*A simple swapping algorithm

        >nums is the array being manipulated
        >a is the first item being swapped
        >b is the second item being swapped
     */
    private static void swap(int[] nums, int a, int b) {
        int temp;
        temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }


    //A HYBRID QUICK SORT ALGORITHM THAT USES A BREAKPOINT.
    public static void hybridQuickSort(int[] nums) {
        hybridQuickSort(nums, 0, nums.length - 1);
    }

    private static void hybridQuickSort(int[] nums, int start, int end) {
        if (end - start > BREAKPOINT)//run if there are at least two items to compare
        {
            medianOf3(nums, start, end - 1);//choose a non-terrible pivot
            int middle = partition(nums, start, end - 1);//approx. sort to get the pivot in the middle

            hybridQuickSort(nums, start, middle);//rec. sort the first half
            hybridQuickSort(nums, middle, end - 1);//rec. sort the second half
        }//END IF
        else {
            insertionSort(nums, start, end - 1);//non-recursive sort the rest of the array
        }
    }

    //--------------------------------------------------------------------------

    //      NON SORTING WORK
    //A METHOD THAT VERIFIES THAT AN ARRAY IS IN SORTED ORDER
    public static boolean isSorted(int[] nums, String sortType, boolean shouldBeOrdered) {
        int currentSortedIndex = 0;
        while (currentSortedIndex < nums.length && nums[currentSortedIndex] < nums[currentSortedIndex + 1]) {
            currentSortedIndex++;
        }//look for a place where the data is out of order


        if (!(nums[currentSortedIndex] < nums[currentSortedIndex + 1]) && shouldBeOrdered) {
            System.out.println("Sorting type: " + sortType + " :FAILED at position: " + currentSortedIndex);
            return false;
        }//END IF
        return true;
    }//END ISSORTED


    //A METHOD TO FILL AN ARRAY WITH VALUES TO SORT
    public static void fillArray(int[] nums, int n) {
        for (int i = 0; i < nums.length; i++)//fills the array
        {
            nums[i] = i;
        }


        for (int swapCount = 0; swapCount <= n; swapCount++)//preforms a random 'n' swaps
        {
            swap(nums, (int) ((nums.length) * Math.random()), (int) ((nums.length) * Math.random()));
        }
    }

    //A TESTING METHOD THAT ALLOWS YOU TO COMPARE AND TO VERIFY THESE SORTING METHODS USING SOME SIMPLE STATISTICS

    public static void testAndBenchmark(int numOfTests, int sizeOfTest, int numOfRandomSwaps)
    {
        //make longs[] to store test times
        long[] insertionSortTime = new long[numOfTests];
        long[] mergeSortTime = new long[numOfTests];
        long[] quickSortTime = new long[numOfTests];
        long[] hybridSortTime = new long[numOfTests];
        long startTime;
        long stopTime;
        long timeElapsed;
        boolean shouldBeSorted = true;//used to make isSorted more readable


        //INSERTION SORT
        for(int i=0;i<numOfTests;i++) {
            int[] randomArray = new int[sizeOfTest];
            fillArray(randomArray, numOfRandomSwaps);

            //test if array is random
            assert(isSorted(randomArray, "Random", !shouldBeSorted));

            //test insertion sort
            startTime = System.nanoTime();
            insertionSort(randomArray);
            stopTime = System.nanoTime();

            assert(!isSorted(randomArray,"Insertion Sort", shouldBeSorted));
            //store the time it took to sort
            timeElapsed = stopTime - startTime;
            insertionSortTime[i]=timeElapsed;
        }
        double insertionSortMean= Stats.mean(insertionSortTime);
        double insertionSortStdDev= Stats.standardDeviation(insertionSortTime);


        //MERGE SORT
        for(int i=0;i<numOfTests;i++) {
            //make array
            int[] randomArray = new int[sizeOfTest];
            fillArray(randomArray, numOfRandomSwaps);

            //test if array is random
            assert(isSorted(randomArray, "Random", !shouldBeSorted));

            //test merge sort
            startTime = System.nanoTime();
            mergeSort(randomArray);
            stopTime = System.nanoTime();

            assert(!isSorted(randomArray,"Merge Sort", shouldBeSorted));
            //store the time it took to sort
            timeElapsed = stopTime - startTime;
            mergeSortTime[i]=timeElapsed;
        }
        double mergeSortMean= Stats.mean(mergeSortTime);
        double mergeSortStdDev= Stats.standardDeviation(mergeSortTime);


        //QUICK SORT
        for(int i=0;i<numOfTests;i++) {
            //make array
            int[] randomArray = new int[sizeOfTest];
            fillArray(randomArray, numOfRandomSwaps);

            //test if array is random
            assert(isSorted(randomArray, "Random", !shouldBeSorted));

            //test quicksort
            startTime = System.nanoTime();
            quicksort(randomArray);
            stopTime = System.nanoTime();

            assert(!isSorted(randomArray,"Quick Sort", shouldBeSorted));
            //store the time it took to sort
            timeElapsed = stopTime - startTime;
            quickSortTime[i]=timeElapsed;
        }
        double quickSortMean= Stats.mean(quickSortTime);
        double quickSortStdDev= Stats.standardDeviation(quickSortTime);


        //HYBRID SORT
        for(int i=0;i<numOfTests;i++) {
            //make array
            int[] randomArray = new int[sizeOfTest];
            fillArray(randomArray, numOfRandomSwaps);

            //test if array is random
            assert(isSorted(randomArray, "Random", !shouldBeSorted));

            //test hybrid quicksort
            startTime = System.nanoTime();
            hybridQuickSort(randomArray);
            stopTime = System.nanoTime();

            assert(!isSorted(randomArray,"Hybrid Quick Sort", shouldBeSorted));
            //store the time it took to sort
            timeElapsed = stopTime - startTime;
            hybridSortTime[i]=timeElapsed;
        }
        double hybridSortMean= Stats.mean(hybridSortTime);
        double hybridSortStdDev= Stats.standardDeviation(hybridSortTime);


         //Z-TESTS
        double insertVsQuickzStat = Stats.zTest(insertionSortTime, quickSortTime);
        double insertVsMergezStat = Stats.zTest(insertionSortTime, mergeSortTime);
        double insertVsHybridzStat = Stats.zTest(insertionSortTime, hybridSortTime);
        double quickVsMergezStat = Stats.zTest(quickSortTime, mergeSortTime);
        double quickVsHybridzStat = Stats.zTest(quickSortTime, hybridSortTime);
        double mergeVsHybridzStat = Stats.zTest(mergeSortTime, hybridSortTime);


        //DISPLAY RESULTS
        System.out.println("All tests  ran successfully! Results:");
        System.out.println("Insertion Sort: \n MEAN: "+String.format("%,.2f",insertionSortMean) +" ns \t \tSTANDARD DEVIATION: "+String.format("%.4f",insertionSortStdDev)+" ns. \n");
        System.out.println("Merge Sort: \n MEAN: "+String.format("%,.2f",mergeSortMean) +" ns \t\tSTANDARD DEVIATION: "+String.format("%.4f",mergeSortStdDev)+" ns. \n");
        System.out.println("Quick Sort: \n MEAN: "+String.format("%,.2f",quickSortMean) +" ns \t\tSTANDARD DEVIATION: "+String.format("%.4f",quickSortStdDev)+" ns. \n");
        System.out.println("Hybrid Quick Sort: \n MEAN: "+String.format("%,.2f",hybridSortMean) +" ns \t\tSTANDARD DEVIATION: "+String.format("%.4f",hybridSortStdDev)+" ns. \n");
        System.out.println("\nZ-TESTS:");
        System.out.println("Insertion vs Quick sort: \t\t"+String.format("%.6f",insertVsQuickzStat)+" \n" +
                            "Insertion vs Merge sort: \t\t"+String.format("%.6f",insertVsMergezStat)+" \n" +
                            "Insertion vs Hybrid Quick sort: "+ String.format("%.6f",insertVsHybridzStat)+ " \n" +
                            "Quick vs Merge sort: \t\t\t"+String.format("%.6f",quickVsMergezStat)+" \n" +
                            "Quick vs Hybrid sort: \t\t\t"+String.format("%.6f",quickVsHybridzStat)+" \n" +
                            "Merge vs Hybrid sort: \t\t\t"+String.format("%.6f",mergeVsHybridzStat)+" \n");

    }//END BENCHMARK AND TEST
}//END STATS
