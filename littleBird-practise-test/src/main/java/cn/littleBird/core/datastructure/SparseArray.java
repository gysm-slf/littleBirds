package cn.littleBird.core.datastructure;

import cn.littleBird.core.SimpleAlgorithm.SortsUtilsImpl;

import java.io.*;

public class SparseArray {
    public static void main(String[] args) throws IOException {
        int chessArr1[][] = new int[11][11];
        chessArr1[1][2]=1;
        chessArr1[2][3]=2;
        chessArr1[10][8]=2;
//        for(int[] row :  chessArr1){
//            // 相当于拆成了11个1维数组   0 11     1  11    2 11    3 11 ... 11 11
//            SortsUtilsImpl.printArrays(row);
//            System.out.println("");
//
////            for(int data:row){
////
////            }
//        }
        int[][] sparseArray = toSparseArray(chessArr1);
        for(int[] row :  sparseArray){
//            SortsUtilsImpl.printArrays(row);
            System.out.println("");
        }

        outputToFile(sparseArray);
        int[][] test = inputToFile(new File("D:\\IDE\\idea_workspace\\storage.txt"));
        for(int[] row :  test){
            SortsUtilsImpl.printArrays(row);
            System.out.println("");
        }

        int[][] array = reductionSparse(sparseArray);
        for(int[] row :  array){
//            SortsUtilsImpl.printArrays(row);
            System.out.println("");
        }

    }

    public static int outputToFile(int[][] sparseArray) throws IOException {
        File file = new File("D:\\IDE\\idea_workspace\\storage.txt");
        FileWriter os = new FileWriter(file);
        for(int[] array:sparseArray){
            for(int data:array){
                os.write(data);
            }
        }
        os.flush();
        if(os!=null)
        os.close();
        return 0;
    }

    public static int[][] inputToFile(File file) throws IOException {
        int len  = 0;
        String[] arr1;
        String str = new String();
        FileReader fr = new FileReader(file);
        while ((len = fr.read())!=-1){
            str=str+len+",";
        }
        arr1=str.split(",");
        int[][] array = new int[Integer.valueOf(arr1[2])+1][3];

        int count=0;
        for (int i = 0; i <= Integer.valueOf(arr1[2]); i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = Integer.valueOf(arr1[count++]);
            }
        }
        if(fr!=null)
            fr.close();
        return array;
    }

    public static int[][] reductionSparse(int[][] sparse){
        int[][] array = new int[sparse[0][0]][sparse[0][1]];
        for (int i = 1; i <= sparse[0][2]; i++) {
            array[sparse[i][0]][sparse[i][1]]=sparse[i][2];
        }
        return array;
    }

    public static int[][] toSparseArray(int[][] array){
        int sum = 0;
        int row  =  array.length;
        int column = array[0].length;
//        for (int[] i : array)
//            column = i.length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if(array[i][j]!=0){
                    sum++;
                }
            }
        }
        int[][] sparseArray =  new int[sum+1][3];
        sparseArray[0][0]=row;
        sparseArray[0][1]=column;
        sparseArray[0][2]=sum;
        int index=0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if(array[i][j]!=0){
                    ++index;
                    sparseArray[index][0]=i;
                    sparseArray[index][1]=j;
                    sparseArray[index][2]=array[i][j];
                }
            }
        }
        return sparseArray;
    }
}
