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

public class A1MorrowLuke {

    //constants
    private static final int BREAKPOINT = 50;
    private static final int NUM_TESTS = 100;
    private static final int TEST_SIZE = 10000;
    private static final int NUM_RANDOM_SWAPS = 2500;

    //main
    public static void main(String[] args)
    {
        testAndBenchmark();
    }//END MAIN




    /* a driver for insertion sort
     */
    private static void insertionSort(int[] nums)
    {
        insertionSort(nums, 0, nums.length-1);
    }


    /* sorts the array by choosing an item and swapping it into its proper place in the sorted part of the array.
     */
    private static void insertionSort(int[] nums, int start, int end) {
        int itemValue;
        int sortingIndex;

        for (int i = start; i <= end; i++) {
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




    /* a driver for merge sort
     */
    private static void mergeSort(int[] nums) {
        int[] temp = new int[nums.length];
        mergeSort(nums, 0, nums.length, temp);
    }


    /* Sorts via recursive merges of smaller sets of data.
     */
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


    /*takes two sets of data and puts the two in sorted order into one set.
     */
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





    /*A driver method for quick sort
     */
    private static void quicksort(int[] nums)
    {
        quicksort(nums, 0, nums.length);
    }


    /*this recursively partitions an ever decreasing part of the array
        to approximately sorted halves, until sorting just three items.
     */
    private static void quicksort(int[] nums, int start, int end) {
        if ((end - start) > 1)//run if there are at least two items to compare
        {
            medianOf3(nums, start, end-1);//choose a non-terrible pivot
            int middle = partition(nums, start, end-1);//approx. sort to get the pivot in the middle

            quicksort(nums, start, middle);//rec. sort the first half
            quicksort(nums, middle+1, end);//rec. sort the second half
        }//END IF
    }//END QUICKSORT


    /*this will approximately sort the two halves using a pivot value
        (the first position item).
     */
    private static int partition(int[] nums, int start, int end) {
        int bigStart = start + 1;//+1 because the pivot is in the first position
        int curr = start + 1;//+1 because the pivot is in the first position
        while (curr <= end) {
            if (nums[curr] < nums[start])//current item is smaller than pivot
            {
                if(bigStart!=curr) {
                    swap(nums, bigStart, curr);
                }
                bigStart++;//make the small side bigger

            }
            curr++;
        }
        swap(nums, start, bigStart - 1);//move pivot to the middle
        return (bigStart - 1);//returns the current position of the pivot
    }//END PARTITION


    /*this method will grab the first, middle, and last item of the
         array and compare to find the median of the 3.
     */
    private static void medianOf3(int[] nums, int start, int end) {
        int middle = start + (end - start) / 2;

        if ((nums[start] <= nums[middle] && nums[middle] <= nums[end]) ||
                (nums[end] <= nums[middle] && nums[middle] <= nums[start]))
            swap(nums, start, middle);    //if middle is the median value

        else if ((nums[middle] <= nums[end] && nums[end] <= nums[start]) ||
                (nums[start] <= nums[end] && nums[end] <= nums[middle]))
            swap(nums, start, end);       //if end is the median value
        //base case is that 0 is the median value
    }//END MEDIANOF3





    /*a driver method for hybrid quick sort
     */
    private static void hybridQuickSort(int[] nums)
    {
        hybridQuickSort(nums, 0, nums.length - 1);
    }


    /* this sort uses a quick sort style of sorting until it reaches it's 'BREAKPOINT', where it switches
        to sorting with insertion sort instead.
     */
    private static void hybridQuickSort(int[] nums, int start, int end) {
        if ((end - start) > BREAKPOINT)//run if there are at least two items to compare
        {
            medianOf3(nums, start, end - 1);//choose a non-terrible pivot
            int middle = partition(nums, start, end);
            hybridQuickSort(nums, start, middle);//rec. sort the first half
            hybridQuickSort(nums, middle+1, end);//rec. sort the second half

        }//END IF
        else {
            insertionSort(nums, start, end);//non-recursive sort the rest of the array
        }//END ELSE
    }//END HYBRID SORT





    /* swaps the position of the two given indexes in the given array
 */
    private static void swap(int[] nums, int a, int b) {
        int temp;
        temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }//END SWAP


    /* this checks if the given array is sorted and if it isn't and it is supposed to be it will
        print out the name of the sort type and the index at which it failed.
     */
    private static boolean isSorted(int[] nums, String sortType, boolean shouldBeOrdered) {
        int currentSortedIndex = 0;
        boolean isOrdered= true;
        while (currentSortedIndex < nums.length-1 && nums[currentSortedIndex] < nums[currentSortedIndex + 1]) {
            currentSortedIndex++;
        }//look for a place where the data is out of order

        if (!((currentSortedIndex == nums.length-1)||(nums[currentSortedIndex] < nums[currentSortedIndex + 1])) && shouldBeOrdered)
        {//check that it is not random
            System.out.println("Sorting type: " + sortType + " :FAILED at position: " + currentSortedIndex);
            isOrdered= false;
        }else if(!shouldBeOrdered && (currentSortedIndex == nums.length-1))
        {//check that it is random
            System.out.println("Array was not random.");
            isOrdered=false;
        }
        return isOrdered;
    }//END ISSORTED


    /* this fills an array linearly and preforms 'NUM_RANDOM_SWAPS' swaps on it to randomize it.
     */
    private static void fillArray(int[] nums) {
        for (int i = 0; i < nums.length; i++)//fills the array
        {
            nums[i] = i;
        }


        for (int swapCount = 0; swapCount <= NUM_RANDOM_SWAPS; swapCount++)//preforms a random 'n' swaps
        {
            swap(nums, (int) ((nums.length) * Math.random()), (int) ((nums.length) * Math.random()));
        }
    }




    /* this runs each test every type of sort, and collects data to be presented by the print statements
        at the end of the method.

        NOTE: uses 'assert' to check if arrays are properly randomized or properly sorted.
     */
    private static void testAndBenchmark()
    {
        //make longs[] to store test times
        long[] insertionSortTime = new long[NUM_TESTS];
        long[] mergeSortTime = new long[NUM_TESTS];
        long[] quickSortTime = new long[NUM_TESTS];
        long[] hybridSortTime = new long[NUM_TESTS];
        long startTime;
        long stopTime;
        long timeElapsed;
        boolean shouldBeSorted = true;//used to make isSorted calls more readable


        //INSERTION SORT
        for(int i=0;i<NUM_TESTS;i++) {
            int[] randomArray = new int[TEST_SIZE];
            fillArray(randomArray);

            //test if array is random
            assert(isSorted(randomArray, "Random", !shouldBeSorted));

            //test insertion sort
            startTime = System.nanoTime();
            insertionSort(randomArray);
            stopTime = System.nanoTime();

            assert(isSorted(randomArray,"Insertion Sort", shouldBeSorted));
            //store the time it took to sort
            timeElapsed = stopTime - startTime;
            insertionSortTime[i]=timeElapsed;
        }
        double insertionSortMean= Stats.mean(insertionSortTime);
        double insertionSortStdDev= Stats.standardDeviation(insertionSortTime);


        //MERGE SORT
        for(int i=0;i<NUM_TESTS;i++) {
            //make array
            int[] randomArray = new int[TEST_SIZE];
            fillArray(randomArray);

            //test if array is random
            assert(isSorted(randomArray, "Random", !shouldBeSorted));

            //test merge sort
            startTime = System.nanoTime();
            mergeSort(randomArray);
            stopTime = System.nanoTime();

            assert(isSorted(randomArray,"Merge Sort", shouldBeSorted));
            //store the time it took to sort
            timeElapsed = stopTime - startTime;
            mergeSortTime[i]=timeElapsed;
        }
        double mergeSortMean= Stats.mean(mergeSortTime);
        double mergeSortStdDev= Stats.standardDeviation(mergeSortTime);


        //QUICK SORT
        for(int i=0;i<NUM_TESTS;i++) {
            //make array
            int[] randomArray = new int[TEST_SIZE];
            fillArray(randomArray);

            //test if array is random
            assert(isSorted(randomArray, "Random", !shouldBeSorted));

            //test quicksort
            startTime = System.nanoTime();
            quicksort(randomArray);
            stopTime = System.nanoTime();

            assert(isSorted(randomArray,"Quick Sort", shouldBeSorted));
            //store the time it took to sort
            timeElapsed = stopTime - startTime;
            quickSortTime[i]=timeElapsed;
        }
        double quickSortMean= Stats.mean(quickSortTime);
        double quickSortStdDev= Stats.standardDeviation(quickSortTime);


        //HYBRID SORT
        for(int i=0;i<NUM_TESTS;i++) {
            //make array
            int[] randomArray = new int[TEST_SIZE];
            fillArray(randomArray);

            //test if array is random
            assert(isSorted(randomArray, "Random", !shouldBeSorted));

            //test hybrid quicksort
            startTime = System.nanoTime();
            hybridQuickSort(randomArray);
            stopTime = System.nanoTime();

            assert(isSorted(randomArray,"Hybrid Quick Sort", shouldBeSorted));
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

        //means and standard deviations
        System.out.println("Insertion Sort: \n MEAN: "+String.format("%,.2f",insertionSortMean) +" ns \t \tSTANDARD DEVIATION: "+String.format("%.4f",insertionSortStdDev)+" ns. \n");
        System.out.println("Merge Sort: \n MEAN: "+String.format("%,.2f",mergeSortMean) +" ns \t\tSTANDARD DEVIATION: "+String.format("%.4f",mergeSortStdDev)+" ns. \n");
        System.out.println("Quick Sort: \n MEAN: "+String.format("%,.2f",quickSortMean) +" ns \t\tSTANDARD DEVIATION: "+String.format("%.4f",quickSortStdDev)+" ns. \n");
        System.out.println("Hybrid Quick Sort: \n MEAN: "+String.format("%,.2f",hybridSortMean) +" ns \t\tSTANDARD DEVIATION: "+String.format("%.4f",hybridSortStdDev)+" ns. \n");

        //z-tests
        System.out.println("\nZ-TESTS:");
        System.out.println("Insertion vs Quick sort: \t\t"+String.format("%.6f",insertVsQuickzStat)+" \n" +
                "Insertion vs Merge sort: \t\t"+String.format("%.6f",insertVsMergezStat)+" \n" +
                "Insertion vs Hybrid Quick sort: "+ String.format("%.6f",insertVsHybridzStat)+ " \n" +
                "Quick vs Merge sort: \t\t\t"+String.format("%.6f",quickVsMergezStat)+" \n" +
                "Quick vs Hybrid sort: \t\t\t"+String.format("%.6f",quickVsHybridzStat)+" \n" +
                "Merge vs Hybrid sort: \t\t\t"+String.format("%.6f",mergeVsHybridzStat)+" \n");

        System.out.println("\nProgram End.");

    }//END BENCHMARK AND TEST
}//END A1MORROWLUKE
