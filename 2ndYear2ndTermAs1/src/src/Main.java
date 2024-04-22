import java.io.IOException;
import java.util.*;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
class Main {
    public static int[] getRandomElements(int[] bigArray, int numElements) {
        Random random = new Random();
        int[] smallArray = new int[numElements];

        for (int i = 0; i < numElements; i++) {
            int randomIndex = random.nextInt(bigArray.length);
            smallArray[i] = bigArray[randomIndex];
        }

        return smallArray;
    }
    public static void main(String args[]) throws IOException {

        InsertionSort iS = new InsertionSort();
        MergeSort mS = new MergeSort();
        CountingSort cS = new CountingSort();
        LinearSearch lS = new LinearSearch();
        BinarySearch bS = new BinarySearch();
        ReadCsv rc = new ReadCsv();
        Random rand = new Random();

        //Read Data
        String inputPath = args[0];
        int[] allRows = rc.readLastColumn(251000, 6,inputPath);
        int[] randomDataSizes = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};
        String[] dataNames = {"500", "1000", "2000", "4000", "8000", "16000", "32000", "64000", "128000", "250000"};

        //INSERTION SORT EXPERIMENT IS WITH RANDOM DATA
        double[] iSRandom =new double[10];
         for (int i = 0; i < randomDataSizes.length; i++) {
            int totalTime = 0;
            for (int j = 0; j < 10; j++) {
                int[] random_Data = getRandomElements(allRows,randomDataSizes[i]);

                long startTime = System.currentTimeMillis();
                iS.insertion_sort(random_Data);
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                totalTime += (int) duration;
            }

            long average =  (totalTime / 10);
            iSRandom[i]=average;
            System.out.println("INSERTION SORT average time of RANDOM data size "+dataNames[i]+" : "+ average+" ms");
        }
        System.out.println("\n");

        //MERGE SORT EXPERIMENT IS WITH RANDOM DATA
        double[] mSRandom =new double[10];
        for (int i = 0; i < randomDataSizes.length; i++) {
            int totalTime = 0;
            for (int j = 0; j < 10; j++) {
                int[] random_Data = getRandomElements(allRows,randomDataSizes[i]);

                long startTime = System.currentTimeMillis();
                int[] mergeSorted =mS.merge_sort(random_Data);
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                totalTime += (int) duration;
            }
            long average =  (totalTime / 10);
            mSRandom[i]=average;
            System.out.println("MERGE SORT average time of RANDOM data size "+dataNames[i]+" : "+ average+" ms");
        }
        System.out.println("\n");
        //COUNTING SORT EXPERIMENT IS WITH RANDOM DATA
        double[] cSRandom =new double[10];
        for (int i = 0; i < randomDataSizes.length; i++) {
            int totalTime = 0;
            for (int j = 0; j < 10; j++) {
                int[] random_Data = getRandomElements(allRows,randomDataSizes[i]);

                long startTime = System.currentTimeMillis();
                int[] countingSorted =cS.countingSort(random_Data);
                long endTime = System.currentTimeMillis();

                long duration = endTime - startTime;
                totalTime += (int) duration;
            }
            long average =  (totalTime / 10);
            cSRandom[i]=average;
            System.out.println("COUNTING SORT average time of RANDOM data size "+dataNames[i]+" : "+ average+" ms");
        }

        System.out.println("\n");
        // INSERTION SORT EXPERIMENT IS WITH SORTED DATA
        double[] iSSorted =new double[10];
        for (int i = 0; i < randomDataSizes.length; i++) {
            int totalTime = 0;
            for (int j = 0; j < 10; j++) {
                int[] random_Data = getRandomElements(allRows,randomDataSizes[i]);
                int[] sorted_Data = mS.merge_sort(random_Data);

                long startTime = System.currentTimeMillis();
                iS.insertion_sort(sorted_Data);
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                totalTime += (int) duration;
            }
            double average =   (double) (totalTime) /10;
            iSSorted[i]=average;
            System.out.println("INSERTION SORT average time of SORTED data size "+dataNames[i]+" : "+ average+" ms");
        }
        System.out.println("\n");
        // MERGE SORT EXPERIMENT IS WITH SORTED DATA
        double[] mSSorted =new double[10];
        for (int i = 0; i < randomDataSizes.length; i++) {
            int totalTime = 0;
            for (int j = 0; j < 10; j++) {
                int[] random_Data = getRandomElements(allRows,randomDataSizes[i]);
                int[] sorted_Data = mS.merge_sort(random_Data);

                long startTime = System.currentTimeMillis();
                int[] mergeSorted =mS.merge_sort(sorted_Data);
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                totalTime += duration;
            }
            long average =   (totalTime) /10;
            mSSorted[i]=average;
            System.out.println("MERGE SORT average time of SORTED data size "+dataNames[i]+" : "+ average+" ms");
        }
        System.out.println("\n");
        // COUNTING SORT EXPERIMENT IS WITH SORTED DATA
        double[] cSSorted =new double[10];
        for (int i = 0; i < randomDataSizes.length; i++) {
            int totalTime = 0;
            for (int j = 0; j < 10; j++) {
                int[] random_Data = getRandomElements(allRows,randomDataSizes[i]);
                int[] sorted_Data = mS.merge_sort(random_Data);

                long startTime = System.currentTimeMillis();
                int[] countingSorted =cS.countingSort(sorted_Data);
                long endTime = System.currentTimeMillis();

                long duration = endTime - startTime;
                totalTime += duration;
            }
            long average =   (totalTime) /10;
            cSSorted[i]=average;
            System.out.println("COUNTING SORT average time of SORTED data size "+dataNames[i]+" : "+ average+" ms");
        }

        System.out.println("\n");
        //INSERTION SORT EXPERIMENT IS WITH REVERSED DATA
        double[] iSReversed =new double[10];
        for (int i = 0; i < randomDataSizes.length; i++) {
            int totalTime = 0;
            for (int j = 0; j < 10; j++) {
                int[] random_Data = getRandomElements(allRows,randomDataSizes[i]);
                int[] sorted_Data = mS.merge_sort(random_Data);
                int[] reversed_Data = mS.reverseArray(sorted_Data);

                long startTime = System.currentTimeMillis();
                iS.insertion_sort(reversed_Data);
                long endTime = System.currentTimeMillis();

                long duration = endTime - startTime;
                // System.out.println(j+1+"th result "+duration);
                totalTime += (int) duration;
            }
            long average =   (totalTime) /10;
            iSReversed[i] = average;
            System.out.println("INSERTION SORT average time of REVERSED data size "+dataNames[i]+" : "+ average+" ms");
        }
        System.out.println("\n");
        //MERGE SORT EXPERIMENT IS WITH REVERSED DATA
        double[] mSReversed =new double[10];
        for (int i = 0; i < randomDataSizes.length; i++) {
            int totalTime = 0;
            for (int j = 0; j < 10; j++) {
                int[] random_Data = getRandomElements(allRows,randomDataSizes[i]);
                int[] sorted_Data = mS.merge_sort(random_Data);
                int[] reversed_Data = mS.reverseArray(sorted_Data);

                long startTime = System.currentTimeMillis();
                int[] mergeSorted =mS.merge_sort(reversed_Data);
                long endTime = System.currentTimeMillis();

                long duration = endTime - startTime;
                // System.out.println(j+1+"th result "+duration);
                totalTime += duration;
            }
            long average =  (totalTime) /10;
            mSReversed[i] = average;
            System.out.println("MERGE SORT average time of REVERSED data size "+dataNames[i]+" : "+ average+" ms");
        }
        System.out.println("\n");
        //COUNTING SORT EXPERIMENT IS WITH REVERSED DATA
        double[] cSReversed =new double[10];
        for (int i = 0; i < randomDataSizes.length; i++) {
            int totalTime = 0;
            for (int j = 0; j < 10; j++) {
                int[] random_Data = getRandomElements(allRows,randomDataSizes[i]);
                int[] sorted_Data = mS.merge_sort(random_Data);
                int[] reversed_Data = mS.reverseArray(sorted_Data);

                long startTime = System.currentTimeMillis();
                int[] countingSorted =cS.countingSort(reversed_Data);
                long endTime = System.currentTimeMillis();

                long duration = endTime - startTime;
                // System.out.println(j+1+"th result "+duration);
                totalTime += (int) duration;
            }
            long average =  (totalTime) /10;
            cSReversed[i]=average;
            System.out.println("COUNTING SORT average time of REVERSED data size "+dataNames[i]+" : "+ average+" ms");
        }

        System.out.println("\n");
        //LINEAR Search With RANDOM Data
        double[] lSRandom = new double[10];
        for (int i = 0; i < randomDataSizes.length; i++) {
            int totalTime = 0;
            for (int j = 0; j < 1000; j++) {
                int[] random_Data = getRandomElements(allRows,randomDataSizes[i]);
                int randomIndex = rand.nextInt(random_Data.length);
                int elementToSearch = random_Data[randomIndex];

                long startTime = System.nanoTime();
                int index = lS.linearSearch(random_Data,elementToSearch);
                long endTime = System.nanoTime();

                long duration = endTime - startTime;
                totalTime += (int) duration;
            }
            long average =  (totalTime / 1000);
            lSRandom[i]=average;
            System.out.println("LINEAR SEARCH average time of RANDOM data size "+dataNames[i]+" : "+ average+" ns");
        }
        System.out.println("\n");
        //LINEAR Search With Sorted Data
        double[] lSSorted = new double[10];
        for (int i = 0; i < randomDataSizes.length; i++) {
            int totalTime = 0;
            for (int j = 0; j < 1000; j++) {
                int[] random_Data = getRandomElements(allRows,randomDataSizes[i]);
                int randomIndex = rand.nextInt(random_Data.length);
                int elementToSearch = random_Data[randomIndex];
                int[] sortedData= mS.merge_sort(random_Data);

                long startTime = System.nanoTime();
                int index = lS.linearSearch(sortedData,elementToSearch);
                long endTime = System.nanoTime();

                long duration = endTime - startTime;
                totalTime += (int) duration;
            }
            long average =  (totalTime / 1000);
            lSSorted[i] =average;
            System.out.println("LINEAR SEARCH average time of SORTED data size "+dataNames[i]+" : "+ average+" ns");
        }
        System.out.println("\n");
        //BINARY Search With Sorted Data
        double[] bSSorted = new double[10];
        for (int i = 0; i < randomDataSizes.length; i++) {
            int totalTime = 0;
            for (int j = 0; j < 1000; j++) {
                int[] random_Data = getRandomElements(allRows,randomDataSizes[i]);
                int randomIndex = rand.nextInt(random_Data.length);
                int elementToSearch = random_Data[randomIndex];
                int[] sortedData= mS.merge_sort(random_Data);

                long startTime = System.nanoTime();
                int index = bS.binarySearch(sortedData,elementToSearch);
                long endTime = System.nanoTime();

                long duration = endTime - startTime;
                totalTime += (int) duration;
            }
            long average =  (totalTime / 1000);
            bSSorted[i]=average;
            System.out.println("BINARY SEARCH average time of SORTED data size "+dataNames[i]+" : "+ average+" ns");
        }


        // X axis data
        int[] inputAxis = {512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 251282};
        //SORTING
         double[][] yAxis = new double[3][10];
        yAxis[0] = iSRandom; //insertion
        yAxis[1] = mSRandom; //merge
        yAxis[2] = cSRandom;//counting
        // Save the char as .png and show it
        showAndSaveChart("Sorting Algorithms Tests on Random Data", inputAxis, yAxis,"Insertion Sort","Merge Sort","Counting Sort","Milliseconds");

        double[][] yAxis2 = new double[3][10];
        yAxis2[0] = iSSorted; //insertion
        yAxis2[1] = mSSorted; //merge
        yAxis2[2] = cSSorted;//counting
        // Save the char as .png and show it
        showAndSaveChart("Sorting Algorithms Tests on Sorted Data", inputAxis, yAxis2,"Insertion Sort","Merge Sort","Counting Sort","Milliseconds");

        double[][] yAxis3 = new double[3][10];
        yAxis3[0] = iSReversed; //insertion
        yAxis3[1] = mSReversed; //merge
        yAxis3[2] = cSReversed;//counting
        // Save the char as .png and show it
        showAndSaveChart("Sorting Algorithms Tests on Reversely Sorted Data", inputAxis, yAxis3,"Insertion Sort","Merge Sort","Counting Sort","Milliseconds");

        //SEARCHING
        double[][] yAxis4 = new double[3][10];
        yAxis4[0] = lSRandom; //insertion
        yAxis4[1] = lSSorted; //merge
        yAxis4[2] = bSSorted;//counting
        // Save the char as .png and show it
        showAndSaveChart("Searching Algorithms Tests", inputAxis, yAxis4,"Linear Search Random Data","Linear Search Sorted Data","Binary Search","Nanoseconds");


    }
    public static void showAndSaveChart(String title, int[] xAxis, double[][] yAxis,String s1,String s2,String s3,String timeUnit) throws IOException {
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in "+timeUnit).xAxisTitle("Input Size").build();

        // Convert x axis to double[]
        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        chart.addSeries(s1, doubleX, yAxis[0]);
        chart.addSeries(s2, doubleX, yAxis[1]);
        chart.addSeries(s3, doubleX, yAxis[2]);

        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);

        // Show the chart
       // new SwingWrapper(chart).displayChart();
    }
}