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
}
