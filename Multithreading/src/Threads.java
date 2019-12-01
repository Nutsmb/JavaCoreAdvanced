public class Threads {

    static final int NUMBER_OF_THREADS = 2;
    static final int size = 10;//
    static int h = size/NUMBER_OF_THREADS;
    static float[] arr = new float[size];
    static float[] arrEnd = new float[size];

    public static void main(String[] args) {
        //float[] arr = new float[size];
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
        for (int i = 0; i < size; i++) {
            arrEnd[i] = 1;
        }
        for (int i = 0; i < size; i++) {
            System.out.print(arrEnd[i]+" ");
        }
        System.out.println();
        MultiThread();
        for (int i = 0; i < size; i++) {
            System.out.print(arrEnd[i]+" ");
        }
    }

    public static void MultiThread() {
        long initTime = System.currentTimeMillis();
        new Thread(new MyRunnableClass(0)).start();
        new Thread(new MyRunnableClass(1)).start();
        System.out.println(System.currentTimeMillis() - initTime);
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
                    _arr[i] = (float)(_arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.arraycopy(_arr, 0, arrEnd, iteration*h, h);
        }
    }
}


