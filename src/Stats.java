import java.lang.Math;

public class Stats {
    /*
     * Creates a mean based from a passed array
     */
    public static double mean(long data[]) {
        double sum = 0;
        for (int i = 0; i < data.length; i++)
            sum += data[i];
        return sum / (double)data.length;
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
        return Math.sqrt(1/(double)data.length * summation);
    }

    /*
     * Return a z-statistic, difference, reported in standard deviations
     */
    public static double zTest(long left[], long right[]) {
        return (mean(left) - mean(right))/
                Math.sqrt(Math.pow(standardDeviation(left),2) + Math.pow(standardDeviation(right),2));
    }

    /////////my code                        ------------------


    //      SORTING WORK
    //A NON- RECURSIVE INSERTION SORT ALGORITHM
    private static void insertionSort(int[] home)
    {
        int itemValue;
        int sortingIndex;

        for(int i=1; i<home.length; i++)
        {
            itemValue = home[i];//the value we are currently looking to sort
            sortingIndex = i-1;//our search index for the sorted portion

            //while index is in the array and current item is greater than our value keep looking
            while(sortingIndex >= 0 && home[sortingIndex] > itemValue)
            {
               home[sortingIndex+1] = home[sortingIndex];//moves items to make way for the new element
                sortingIndex--;
            }//END WHILE

           home[sortingIndex+1] = itemValue;//add item where while loop stopped

        }//END FOR
    }//END INSERTIONSORT



    //      RECURSIVE WORK

    //A RECURSIVE MERGE SORT ALGORITHM
    private static void mergeSort(int[] home, int start, int end, int[] temp)
    {
        int mid;

        if(1 < end-start)//only run if there is more then 1 item in a list
        {
            mid = start + (end-start)/2;// define the middle of the array

            mergeSort(home, start, mid, temp);//recursively sort the lower half
            mergeSort(home, mid, end, temp);//recursively sort the upper half

            merge (home, start, mid, end, temp);//merge those two halves into one sorted list

        }//END IF
    }//END MERGESORT

    private static void merge(int[] home, int start,int mid, int end, int[] temp)
    {
        int currL = start;
        int currR = mid;
        int currT;

        for(currT = start; currT<end; currT++)
        {
            /*if('L' is in its proper range, and if ('R' has run out of its range
            or 'a[R]' is bigger than 'a[L]')) */
            if(currL < mid && (currR >= end || home[currL] < home[currR]))
            {
                temp[currT]= home[currL];//put 'a[L]' in next
                currL++;
            }//END IF
            else
            {
                temp[currT] =home[currR];
                currR++;
            }//END ELSE
        }//END FOR

        System.arraycopy(temp,0,  home, 0, temp.length);
        //^deep copy the temp array to the home array

    }//END MERGE





    //A RECURSIVE QUICK SORT ALGORITHM

    private void medianOf3(int[] home) {

        int middle = home.length / 2;
        int end = home.length-1;

            if(home[0]<=home[middle] && home[middle]<home[end] ||
                    home[end]<=home[middle] && home[middle]<home[0])
                swap(home,0,middle);    //if middle is the median value
            else if(home[middle]<=home[end] && home[end]<home[0] ||
                    home[0]<=home[end] && home[middle]<home[end])
                swap(home,0,end);       //if end is the median value
            //base case is that 0 is the median value
    }

    private static void swap(int[] home, int a, int b)
    {
        int temp;
        temp = home[a];
        home[a] = home[b];
        home[b] = temp;
    }
    //A HYBRID QUICK SORT ALGORITHM THAT USES A BREAKPOINT.


    //--------------------------------------------------------------------------

    //      NON SORTING WORK
    //A METHOD THAT VERIFIES THAT AN ARRAY IS IN SORTED ORDER

    //A METHOD TO FILL AN ARRAY WITH VALUES TO SORT

    //A TESTING METHOD THAT ALLOWS YOU TO COMPARE AND TO VERIFY THESE SORTING METHODS USING SOME SIMPLE STATISTICS



    //REPORT
    //ANSWER A FEW QUESTIONS
}
