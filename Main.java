import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try {
            Scanner sc = new Scanner(System.in);
            FakeNewsClassifier detector = new FakeNewsClassifier();

            detector.trainModel();

            System.out.println("\n📰 Enter News Text:");
            String news = sc.nextLine();

            String result = detector.predict(news);

            System.out.println("\n🔍 Prediction Result: " + result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
