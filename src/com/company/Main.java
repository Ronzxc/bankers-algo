package com.company;

import java.util.Scanner;

public class Main {
    private static int[][] needArray;
    private static int[][] allocationArray;
    private static int[][] maxArray;
    private static int[] availableArray;
    private static int[] processes;
    private static int[] newAvailableArray;
    static Scanner sc = new Scanner(System.in);
    static int numberOfProcesses, numberOfResources;
    private static int [] totalAllocation;
    private static int [] resInstance;
    private static int [] totalAvailableArray;

    public static int[] findResAvailable(int allocationArray[][]){
        int totalAllocation [] = new int [numberOfResources];
        int totalAvailableArray1 [] = new int [numberOfResources];
        Main.totalAllocation = totalAllocation;
        for (int i = 0; i < numberOfProcesses; i++) {
            for (int j = 0; j < numberOfResources; j++) {
                totalAllocation[j] += allocationArray[i][j];
            }
        }
        for (int i = 0; i < totalAllocation.length; i++) {
            totalAvailableArray1[i] = resInstance[i] - totalAllocation[i];
        }
        Main.totalAvailableArray= totalAvailableArray1;

        return totalAllocation;
    }

    static void findNeedValue(int needArray[][], int maxArray[][], int allocationArray[][], int totalProcess, int totalResources)
    {
        Main.needArray = needArray;
        // use nested for loop to calculate Need for each process
        for (int i = 0 ; i < totalProcess ; i++){    // for each process
            for (int j = 0 ; j < totalResources ; j++){  //for each resource
                needArray[i][j] = maxArray[i][j] - allocationArray[i][j];
            }
        }
    }

    // create checkSafeSystem() method to determine whether the system is in safe state or not
    static boolean checkSafeSystem(int processes[], int availableArray[], int maxArray[][], int allocationArray[][], int totalProcess, int totalResources)
    {
        int [][]needArray = new int[totalProcess][totalResources];
        findResAvailable(allocationArray);

        // call findNeedValue() method to calculate needArray
        findNeedValue(needArray, maxArray, allocationArray, totalProcess, totalResources);

        // all the process should be infinished in starting
        boolean []finishProcesses = new boolean[totalProcess];

        // initialize safeSequenceArray that store safe sequenced
        int []safeSequenceArray = new int[totalProcess];

        // initialize workArray as a copy of the available resources
        int []workArray = new int[totalResources];

        for (int i = 0; i < totalResources ; i++)    //use for loop to copy each available resource in the workArray
            workArray[i] = totalAvailableArray[i];

        // initialize counter variable whose value will be 0 when the system is not in the safe state or when all the processes are not finished.
        int counter = 0;

        // use loop to iterate the statements until all the processes are not finished
        while (counter < totalProcess)
        {
            // find unfinished process which needs can be satisfied with the current work resource.
            boolean foundSafeSystem = false;
            for (int m = 0; m < totalProcess; m++)
            {
                if (!finishProcesses[m])        // when process is not finished
                {
                    int j;

                    //use for loop to check whether the need of each process for all the resources is less than the work
                    for (j = 0; j < totalResources; j++)
                        if (needArray[m][j] > workArray[j])      //check need of current resource for current process with work
                            break;   //workarray > needarray

                    // the value of J and totalResources will be equal when all the needs of current process are satisfied
                    if (j == totalResources)
                    {
                        for (int k = 0 ; k < totalResources ; k++)
                            workArray[k] += allocationArray[m][k];

                        // add current process in the safeSequenceArray
                        safeSequenceArray[counter++] = m;


                        // make this process finished
                        finishProcesses[m] = true;

                        foundSafeSystem = true;
                    }
                }
            }

            // the system will not be in the safe state when the value of the foundSafeSystem is false
            if (!foundSafeSystem)
            {
                System.out.print("The system is not in the safe state because lack of resources");
                return false;
            }
        }

        // print the safe sequence
        System.out.print("The system is in safe sequence and the sequence is as follows: ");
        for (int i = 0; i < totalProcess ; i++)
            System.out.print("P"+safeSequenceArray[i] + " ");

        return true;
    }

    static boolean checkSafeSystemRequest(int processes[], int availableArray[], int maxArray[][], int allocationArray[][], int totalProcess, int totalResources)
    {
        int [][]needArray = new int[totalProcess][totalResources];

        // call findNeedValue() method to calculate needArray
        findNeedValue(needArray, maxArray, allocationArray, totalProcess, totalResources);

        // all the process should be infinished in starting
        boolean []finishProcesses = new boolean[totalProcess];

        // initialize safeSequenceArray that store safe sequenced
        int []safeSequenceArray = new int[totalProcess];

        // initialize workArray as a copy of the available resources
        int []workArray = new int[totalResources];

        for (int i = 0; i < totalResources ; i++)    //use for loop to copy each available resource in the workArray
            workArray[i] = availableArray[i];

        // initialize counter variable whose value will be 0 when the system is not in the safe state or when all the processes are not finished.
        int counter = 0;

        // use loop to iterate the statements until all the processes are not finished
        while (counter < totalProcess)
        {
            // find unfinished process which needs can be satisfied with the current work resource.
            boolean foundSafeSystem = false;
            for (int m = 0; m < totalProcess; m++)
            {
                if (!finishProcesses[m])        // when process is not finished
                {
                    int j;

                    //use for loop to check whether the need of each process for all the resources is less than the work
                    for (j = 0; j < totalResources; j++)
                        if (needArray[m][j] > workArray[j])      //check need of current resource for current process with work
                            break;

                    // the value of J and totalResources will be equal when all the needs of current process are satisfied
                    if (j == totalResources)
                    {
                        for (int k = 0 ; k < totalResources ; k++)
                            workArray[k] += allocationArray[m][k];

                        // add current process in the safeSequenceArray
                        safeSequenceArray[counter++] = m;


                        // make this process finished
                        finishProcesses[m] = true;
                        foundSafeSystem = true;
                        m=-1;

                    }
                }
            }

            // the system will not be in the safe state when the value of the foundSafeSystem is false
            if (!foundSafeSystem)
            {
                System.out.print("The system is not in the safe state because lack of resources");
                return false;
            }
        }

        // print the safe sequence
        System.out.print("The system is in safe sequence and the sequence is as follows: ");
        for (int i = 0; i < totalProcess ; i++)
            System.out.print("P"+safeSequenceArray[i] + " ");

        return true;
    }

    // main() method start
    public static void main(String[] args)
    {
        display();
        int bin = 1;
        while (bin == 1){
            System.out.println("Would you like to request resources? (Press 1 if yes, otherwise press 0)");
            bin = sc.nextInt();
            if(bin ==0){
                break;
            }
            else {
                updateResource();
            }

        }
        System.out.println("Finished");
    }
    public static void display() {

        // get total number of resources from the user
        System.out.println("Enter total number of processes");
        numberOfProcesses = sc.nextInt();

        // get total number of resources from the user
        System.out.println("Enter total number of resources");
        numberOfResources = sc.nextInt();


        int processes[] = new int[numberOfProcesses];
        for (int i = 0; i < numberOfProcesses; i++) {
            processes[i] = i;
        }
        int resInstance[] = new int[numberOfResources];
        for (int i = 0; i < numberOfResources; i++) {
            System.out.println("Enter the resource instance " + i + ": ");
            resInstance[i] = sc.nextInt();
        }
        Main.resInstance = resInstance;

        int maxArray[][] = new int[numberOfProcesses][numberOfResources];
        for (int i = 0; i < numberOfProcesses; i++) {
            for (int j = 0; j < numberOfResources; j++) {
                System.out.println("Enter the maximum resource" + j + " that can be allocated to process" + i + ": ");
                maxArray[i][j] = sc.nextInt();
            }
        }

        int allocationArray[][] = new int[numberOfProcesses][numberOfResources];
        for (int i = 0; i < numberOfProcesses; i++) {
            for (int j = 0; j < numberOfResources; j++) {
                System.out.println("How many instances of resource" + j + " are allocated to process" + i + "? ");
                allocationArray[i][j] = sc.nextInt();
            }
        }


        //call checkSafeSystem() method to check whether the system is in safe state or not
        checkSafeSystem(processes, totalAvailableArray, maxArray, allocationArray, numberOfProcesses, numberOfResources);
        System.out.println();
        System.out.println("Available");
        for (int element : totalAvailableArray) {
            System.out.print(element + " ");
        }
        System.out.println("\nProcess     Allocation      Max             Need");

        System.out.println();
        for (int i = 0; i < Math.max(processes.length, Math.max(totalAvailableArray.length, Math.max(allocationArray.length, Math.max(maxArray.length, needArray.length)))); i++) {
            if (i < processes.length) {
                System.out.print(processes[i] + " \t\t\t");
            }
            if (i < allocationArray.length && maxArray[i].length >= 3) {
                System.out.print(allocationArray[i][0] + " " + allocationArray[i][1] + " " + allocationArray[i][2] + " \t\t\t");
            }
            if (i < maxArray.length && maxArray[i].length >= 3) {
                System.out.print(maxArray[i][0] + " " + maxArray[i][1] + " " + maxArray[i][2] + "\t\t\t");
            }
//              if (i < availableArray.length) {
//                  System.out.print(availableArray[i] + " \t\t");
//              }
            if (i < needArray.length && needArray[i].length >= 3) {
                System.out.print(needArray[i][0] + " " + needArray[i][1] + " " + needArray[i][2] + " \n");
            }
        }

        Main.allocationArray = allocationArray;
        Main.maxArray = maxArray;
        Main.availableArray = availableArray;
        Main.processes = processes;
    }
    public static void updateResource(){
        System.out.println("===================Request a Resource===================");
        System.out.println("Enter what Process will be? ");
        int processNum = sc.nextInt();
        boolean isLessThanOrEqualTo = true;
        int request[] = new int[3];

        for(int i = 0; i<3; i++){
            if (i==0){
                System.out.print("Enter value for resource 0 :");
                request[i] = sc.nextInt();
            }
            if (i==1){
                System.out.print("Enter value for resource 1 :");
                request[i] = sc.nextInt();
            }
            if (i==2){
                System.out.print("Enter value for resource 2 :");
                request[i] = sc.nextInt();
            }
        }
        if(request[0] <= needArray[processNum][0]){
            System.out.println("The index 0 of request is granted");
            if(request[0] <= totalAvailableArray[0]){
                System.out.println("The index 0 of request is granted");
            }
            else{
                System.out.println("resources are not available");
                isLessThanOrEqualTo = false;
            }
        }
        if(request[1] <= needArray[processNum][1]){
            System.out.println("The index 1 of request is granted");
            if(request[1] <= totalAvailableArray[1]){
                System.out.println("The index 1 of request is granted");
            }
            else{
                System.out.println("resources are not available");
                isLessThanOrEqualTo = false;
            }

        }
        if(request[2] <= needArray[processNum][2]){
            System.out.println("The index 2 of request is granted");
            if(request[2] <= totalAvailableArray[2]){
                System.out.println("The index 2 of request is granted");
            }
            else{
                System.out.println("resources are not available");
                isLessThanOrEqualTo = false;
            }
        }
        else{
            System.out.println("Exceeding its maximum claim");
            isLessThanOrEqualTo = false;
        }
        if(isLessThanOrEqualTo) {
            int[] newAvailableArray = new int[totalAvailableArray.length];

            for (int j = 0; j < totalAvailableArray.length; j++) {
                newAvailableArray[j] = totalAvailableArray[j] - request[j];
            }
            totalAvailableArray = newAvailableArray;

            Main.newAvailableArray = newAvailableArray;

            updateAllocation(allocationArray, processNum, request);
            updateNeed(needArray, processNum, request);
            displayRequest();
        }
        else{
            System.out.println("The Request Resource didn't meet the conditions!");
        }

    }
    public static void displayRequest(){

        checkSafeSystemRequest(processes, newAvailableArray, maxArray, allocationArray, numberOfProcesses, numberOfResources);

        System.out.println();
        System.out.println("Available");
        for (int element: newAvailableArray) {
            System.out.print(element + " ");
        }

        System.out.println();
        System.out.println("\nProcess     Allocation      Max             Need");

        System.out.println();

        for (int i = 0; i < Math.max(processes.length, Math.max(totalAvailableArray.length, Math.max(allocationArray.length, Math.max(maxArray.length, needArray.length)))); i++) {
            if (i < processes.length) {
                System.out.print(processes[i] + " \t\t\t");
            }
            if (i < allocationArray.length && maxArray[i].length >= 3) {
                System.out.print(allocationArray[i][0] + " " + allocationArray[i][1] + " " + allocationArray[i][2] + " \t\t\t");
            }
            if (i < maxArray.length && maxArray[i].length >= 3) {
                System.out.print(maxArray[i][0] + " " + maxArray[i][1] + " " + maxArray[i][2] + "\t\t\t");
            }
//              if (i < availableArray.length) {
//                  System.out.print(availableArray[i] + " \t\t");
//              }
            if (i < needArray.length && needArray[i].length >= 3) {
                System.out.print(needArray[i][0] + " " + needArray[i][1] + " " + needArray[i][2] + " \n");
            }
        }
    }
    public static void updateAllocation(int[][] arr, int rowIndex, int[] newRow) {
        if (rowIndex >= 0 && rowIndex < arr.length && newRow.length == arr[rowIndex].length) {
            for (int i = 0; i < arr[rowIndex].length; i++) {
                arr[rowIndex][i] = newRow[i] + arr[rowIndex][i];
            }
            System.out.println("Row updated successfully!");
        } else {
            System.out.println("Row update failed. Please check the input parameters.");
        }
    }
    public static void updateNeed(int[][] arr, int rowIndex, int[] newRow) {
        if (rowIndex >= 0 && rowIndex < arr.length && newRow.length == arr[rowIndex].length) {
            for (int i = 0; i < arr[rowIndex].length; i++) {
                arr[rowIndex][i] = arr[rowIndex][i] - newRow[i] ;
            }
            System.out.println("Row updated successfully!");
        } else {
            System.out.println("Row update failed. Please check the input parameters.");
        }
    }
}