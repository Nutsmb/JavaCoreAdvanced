public class Threads {

    static final int NUMBER_OF_THREADS = 2;
    static final int size = 10000000; // размер первоначального массива
    static int h = size/NUMBER_OF_THREADS; // количество элементов в массиве потока
    static float[] arr = new float[size]; // первоначальный массив
    static float[] arrThreads = new float[size]; // массив, склееный из массивов потоков
    static float[] arrLonely = new float[size]; // массив после обработки в одном потоке

    public static void main(String[] args) {
        //заполняем массив единицами
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }

        MultiThread();

        System.out.println();

        lonelly();
    }

    public static void lonelly(){
        long ini = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            arrLonely[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Time for lonelly thread " + (System.currentTimeMillis() - ini));
    }

    public static void MultiThread() {
        long initTime = System.currentTimeMillis();
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            new Thread(new MyRunnableClass(i)).start();
        }
        System.out.println("Time for multithread " + (System.currentTimeMillis() - initTime));
    }

    static class MyRunnableClass implements Runnable {
        float[] _arr = new float[h];
        int iteration;

        public MyRunnableClass(int n) {
            System.arraycopy(arr, n*h, _arr, 0, h);
            iteration = n;
        }

        @Override
        public void run() {
            for (int i = 0; i < h; i++) {
                try {
                    _arr[i] = (float)(_arr[i] * Math.sin(0.2f + (i+iteration * h) / 5) * Math.cos(0.2f + (i+iteration * h) / 5) * Math.cos(0.4f + (i+iteration * h) / 2));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.arraycopy(_arr, 0, arrThreads, iteration*h, h);
        }
    }
}


