public class Exceptions {
    public static void main(String[] args) {
        int N = 4; // тут можно изменить количество ячеек для ловли MyArraySizeException
        int M = 4; // и тут тоже

        String[][] Arr = new String[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Arr[i][j] = String.valueOf(Math.round(Math.random() * 10));
            }
        }
    }

    public static class  MyArraySizeException extends Exception {
        public MyArraySizeException(int rows, int columns){
            int A = 4; // при превышении А строк выкинет исключение
            int B = 4; // при превышении В столбцов выкинет исключение
            System.out.print("Неверный размер массива. ");
            if(rows > A){
                System.out.println("Строк должно быть " + A + ", а не " + rows + ". ");
            }
            else if (columns > B){
                System.out.println( "Cтолбцов должно быть " + B + ", а не " + columns);
            }
        }
    }
}
