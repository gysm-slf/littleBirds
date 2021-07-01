package cn.littleBird.core.SimpleAlgorithm;


import java.util.Arrays;
import java.util.Random;

//[12,45,3,5,9,1,10,46,88,100,2,520,66]
//https://javajgs.com/archives/1599#4Shell_Sort_63
public class SortsUtilsImpl implements SortsUtils {

    public static void main(String[] args) {
        Random random = new Random();
        int[] test = new int[100000];
        for (int i = 0; i < test.length; i++) {
            test[i] = random.nextInt(20000);
        }
        printArrays(new SortsUtilsImpl().insertSort(test));
    }

    public static void printArrays(int[] array){
        System.out.print("[");
        for (int i = 0; i < array.length-1; i++) {
            System.out.print(array[i]+",");
        }
        System.out.print(array[array.length-1]+"]");
    }

    public static int[] halfSort(int[] array){
        int left = 0;int right = array.length;
        int mid = (left + right)/2;
        for (int i = 0; i < array.length / 2; i++) {

        }
        return array;
    }

    //1.冒泡排序算法
    public int[] bubbleSort(int[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < args.length - 1; i++) {
            for (int j = i + 1; j < args.length; j++) {
                if (args[i] > args[j]) {
                    int temp = args[i];
                    args[i] = args[j];
                    args[j] = temp;
                }
            }
        }
        System.out.println("it cost:"+(System.currentTimeMillis()-start)+"ms");
        return args;
    }

    //2.选择排序算法
    public int[] selectSort(int[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < args.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < args.length; j++) {
                if (args[min] > args[j]) {
                    min = j;
                }
            }
            if (min != i) {
                int temp = args[i];
                args[i] = args[min];
                args[min] = temp;
            }
        }
        System.out.println("it cost:"+(System.currentTimeMillis()-start)+"ms");
        return args;
    }

    //3.插入排序算法
    public int[] insertSort(int[] args) {
        long start = System.currentTimeMillis();
        for (int i = 1; i < args.length; i++) {
            for (int j = i; j > 0; j--) {
                if (args[j] < args[j - 1]) {
                    int temp = args[j - 1];
                    args[j - 1] = args[j];
                    args[j] = temp;
                } else break;
            }
        }
        System.out.println("it cost:"+(System.currentTimeMillis()-start)+"ms");
        return args;
    }

    /**
     * Description: 4.希尔排序
     *
     * 算法分析
     * 最佳情况：T(n) = O(nlog2 n) 最坏情况：T(n) = O(nlog2 n) 平均情况：T(n) =O(nlog2n)
     * @param array
     * @return void
     * @author JourWon
     * @date 2019/7/11 23:34
     */
    public static void shellSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        int length = array.length;

        // temp为临时变量，gap增量默认是长度的一半，每次变为之前的一半，直到最终数组有序
        int temp, gap = length / 2;

        while (gap > 0) {
            for (int i = gap; i < length; i++) {
                // 将当前的数与减去增量之后位置的数进行比较，如果大于当前数，将它后移
                temp = array[i];
                int preIndex = i - gap;

                while (preIndex >= 0 && array[preIndex] > temp) {
                    array[preIndex + gap] = array[preIndex];
                    preIndex -= gap;
                }

                // 将当前数放到空出来的位置
                array[preIndex + gap] = temp;

            }
            gap /= 2;
        }
    }

    /**
     * Description: 5.归并排序
     *
     * 算法分析
     *
     * 最佳情况：T(n) = O(n) 最差情况：T(n) = O(nlogn) 平均情况：T(n) = O(nlogn)
     * @param array
     * @return void
     * @author JourWon
     * @date 2019/7/11 23:37
     */
    public static void mergeSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        sort(array, 0, array.length - 1);
    }

    private static void sort(int[] array, int left, int right) {
        if (left == right) {
            return;
        }
        int mid = left + ((right - left) >> 1);
        // 对左侧子序列进行递归排序
        sort(array, left, mid);
        // 对右侧子序列进行递归排序
        sort(array, mid + 1, right);
        // 合并
        merge(array, left, mid, right);
    }

    private static void merge(int[] array, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = 0;
        int p1 = left;
        int p2 = mid + 1;
        // 比较左右两部分的元素，哪个小，把那个元素填入temp中
        while (p1 <= mid && p2 <= right) {
            temp[i++] = array[p1] < array[p2] ? array[p1++] : array[p2++];
        }
        // 上面的循环退出后，把剩余的元素依次填入到temp中
        // 以下两个while只有一个会执行
        while (p1 <= mid) {
            temp[i++] = array[p1++];
        }
        while (p2 <= right) {
            temp[i++] = array[p2++];
        }
        // 把最终的排序的结果复制给原数组
        for (i = 0; i < temp.length; i++) {
            array[left + i] = temp[i];
        }
    }

    /**
     * Description: 6.堆排序
     *
     * @param array
     * @return void
     * @author JourWon
     * @date 2019/7/11 23:40
     */
    public static void heapSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        int length = array.length;

        //1.构建大顶堆
        for (int i = length / 2 - 1; i >= 0; i--) {
            //从第一个非叶子结点从下至上，从右至左调整结构
            adjustHeap(array, i, length);
        }
        //2.调整堆结构+交换堆顶元素与末尾元素
        for (int j = length - 1; j > 0; j--) {
            //将堆顶元素与末尾元素进行交换
            swap(array, 0, j);
            //重新对堆进行调整
            adjustHeap(array, 0, j);
        }

    }

    /**
     * Description: 调整大顶堆（仅是调整过程，建立在大顶堆已构建的基础上）
     *
     * 算法分析
     *
     * 最佳情况：T(n) = O(nlogn) 最差情况：T(n) = O(nlogn) 平均情况：T(n) = O(nlogn)
     * @param array
     * @param i
     * @param length
     * @return void
     * @author JourWon
     * @date 2019/7/11 17:58
     */
    private static void adjustHeap(int[] array, int i, int length) {
        //先取出当前元素i
        int temp = array[i];
        //从i结点的左子结点开始，也就是2i+1处开始
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
            //如果左子结点小于右子结点，k指向右子结点
            if (k + 1 < length && array[k] < array[k + 1]) {
                k++;
            }
            //如果子节点大于父节点，将子节点值赋给父节点（不用进行交换）
            if (array[k] > temp) {
                array[i] = array[k];
                i = k;
            } else {
                break;
            }
        }
        //将temp值放到最终的位置
        array[i] = temp;
    }

    /**
     * Description: 交换元素位置
     *
     * @param array
     * @param a
     * @param b
     * @return void
     * @author JourWon
     * @date 2019/7/11 17:57
     */
    private static void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    /**
     * Description: 7.计数排序
     *
     * 算法分析
     * 当输入的元素是n 个0到k之间的整数时，它的运行时间是 O(n + k)。计数排序不是比较排序，排序的速度快于任何比较排序算法。由于用来计数的数组C的长度取决于待排序数组中数据的范围（等于待排序数组的最大值与最小值的差加上1），这使得计数排序对于数据范围很大的数组，需要大量时间和内存。
     * 最佳情况：T(n) = O(n+k) 最差情况：T(n) = O(n+k) 平均情况：T(n) = O(n+k)
     * @param array
     * @return void
     * @author JourWon
     * @date 2019/7/11 23:42
     */
    public static void countingSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        int length = array.length;

        int max = array[0];
        int min = array[0];
        for (int i = 0; i < length; i++) {
            if (max < array[i]) {
                max = array[i];
            }
            if (min > array[i]) {
                min = array[i];
            }
        }
        // 最大最小元素之间范围[min, max]的长度
        int offset = max - min + 1;
        // 1. 计算频率，在需要的数组长度上额外加1
        int[] count = new int[offset + 1];
        for (int i = 0; i < length; i++) {
            // 使用加1后的索引，有重复的该位置就自增
            count[array[i] - min + 1]++;
        }
        // 2. 频率 -> 元素的开始索引
        for (int i = 0; i < offset; i++) {
            count[i + 1] += count[i];
        }

        // 3. 元素按照开始索引分类，用到一个和待排数组一样大临时数组存放数据
        int[] aux = new int[length];
        for (int i = 0; i < length; i++) {
            // 填充一个数据后，自增，以便相同的数据可以填到下一个空位
            aux[count[array[i] - min]++] = array[i];
        }
        // 4. 数据回写
        for (int i = 0; i < length; i++) {
            array[i] = aux[i];
        }
    }

    /**
     * Description: 8.基数排序
     *
     * 算法分析
     * 最佳情况：T(n) = O(n * k) 最差情况：T(n) = O(n * k) 平均情况：T(n) = O(n * k)
     * 基数排序有两种方法：
     * MSD 从高位开始进行排序 LSD 从低位开始进行排序
     * 基数排序 vs 计数排序 vs 桶排序
     * 这三种排序算法都利用了桶的概念，但对桶的使用方法上有明显差异：
     *     基数排序：根据键值的每位数字来分配桶
     *     计数排序：每个桶只存储单一键值
     *     桶排序：每个桶存储一定范围的数值
     * @param array
     * @return void
     * @author JourWon
     * @date 2019/7/11 23:45
     */
    public static void radixSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        int length = array.length;

        // 每位数字范围0~9，基为10
        int radix = 10;
        int[] aux = new int[length];
        int[] count = new int[radix + 1];
        // 以关键字来排序的轮数，由位数最多的数字决定，其余位数少的数字在比较高位时，自动用0进行比较
        // 将数字转换成字符串，字符串的长度就是数字的位数，字符串最长的那个数字也拥有最多的位数
        int x = Arrays.stream(array).map(s -> String.valueOf(s).length()).max().getAsInt();

        // 共需要d轮计数排序, 从d = 0开始，说明是从个位开始比较，符合从右到左的顺序
        for (int d = 0; d < x; d++) {
            // 1. 计算频率，在需要的数组长度上额外加1
            for (int i = 0; i < length; i++) {
                // 使用加1后的索引，有重复的该位置就自增
                count[digitAt(array[i], d) + 1]++;
            }
            // 2. 频率 -> 元素的开始索引
            for (int i = 0; i < radix; i++) {
                count[i + 1] += count[i];
            }

            // 3. 元素按照开始索引分类，用到一个和待排数组一样大临时数组存放数据
            for (int i = 0; i < length; i++) {
                // 填充一个数据后，自增，以便相同的数据可以填到下一个空位
                aux[count[digitAt(array[i], d)]++] = array[i];
            }
            // 4. 数据回写
            for (int i = 0; i < length; i++) {
                array[i] = aux[i];
            }
            // 重置count[]，以便下一轮统计使用
            for (int i = 0; i < count.length; i++) {
                count[i] = 0;
            }

        }
    }

    /**
     * Description: 根据d，获取某个值的个位、十位、百位等，d = 0取出个位，d = 1取出十位，以此类推。对于不存在的高位，用0补
     *
     * @param value
     * @param d
     * @return int
     * @author JourWon
     * @date 2019/7/11 23:46
     */
    private static int digitAt(int value, int d) {
        return (value / (int) Math.pow(10, d)) % 10;
    }

    /**
     * Description: 快速排序
     *
     * 算法分析
     * 最佳情况：T(n) = O(nlogn) 最差情况：T(n) = O(n2) 平均情况：T(n) = O(nlogn)
     * @param array
     * @return void
     * @author JourWon
     * @date 2019/7/11 23:39
     */
    public static void quickSort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }


    private static void quickSort(int[] array, int left, int right) {
        if (array == null || left >= right || array.length <= 1) {
            return;
        }
        int mid = partition(array, left, right);
        quickSort(array, left, mid);
        quickSort(array, mid + 1, right);
    }


    private static int partition(int[] array, int left, int right) {
        int temp = array[left];
        while (right > left) {
            // 先判断基准数和后面的数依次比较
            while (temp <= array[right] && left < right) {
                --right;
            }
            // 当基准数大于了 arr[left]，则填坑
            if (left < right) {
                array[left] = array[right];
                ++left;
            }
            // 现在是 arr[right] 需要填坑了
            while (temp >= array[left] && left < right) {
                ++left;
            }
            if (left < right) {
                array[right] = array[left];
                --right;
            }
        }
        array[left] = temp;
        return left;
    }
}
