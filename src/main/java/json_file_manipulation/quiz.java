package json_file_manipulation;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Random;


public class quiz {
    public static void main(String[] args) throws IOException, ParseException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose an option: \n1.Add Questions\n2.Start Quiz");
        System.out.print("Select one: ");
        int option = sc.nextInt();

        if(option == 1){
            questionBank();

        }else if(option == 2){
            startQuiz();
        }
    }
    private static void questionBank() throws IOException, ParseException {
        char ch;
        String fileName = "./src/main/resources/QuestionBank.json";
        //noinspection ReassignedVariable
        do{
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(new FileReader(fileName));

            Scanner sc = new Scanner(System.in);
            System.out.println("Please add a question: ");
            HashMap<String, String> questionBank = new HashMap<>();
            questionBank.put("question", sc.nextLine());

            System.out.println("Enter the options: ");
            questionBank.put("Option a", sc.nextLine());
            questionBank.put("Option b", sc.nextLine());
            questionBank.put("Option c", sc.nextLine());
            questionBank.put("Option d", sc.nextLine());
            System.out.print("Please input the correct answer: ");
            questionBank.put("answer", sc.next());

            JSONArray questionBankArray = (JSONArray) obj;
            questionBankArray.add(questionBank);

            FileWriter file = new FileWriter(fileName);
            file.write(questionBankArray.toJSONString());

            file.flush();
            file.close();
            System.out.println("\nDo you want to add more questions?[y/n]");
            ch = sc.next().charAt(0);
        }
        while (ch != 'n');
    }

    public static void startQuiz() throws IOException, ParseException {
        int point = 0;

        String fileName = "./src/main/resources/QuestionBank.json";

        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(new FileReader(fileName));
        JSONArray quizArray = (JSONArray) obj;
        for (int i = 0; i < 5; i++) {
            Random rand = new Random();
            int index = rand.nextInt(quizArray.size());
            int previousIndex = index;
            while (index == previousIndex){
                index = rand.nextInt(quizArray.size());
            }
            JSONObject quizObject = (JSONObject) quizArray.get(index);
            String name = (String) quizObject.get("question");
            String a = (String) quizObject.get("Option a");
            String b = (String) quizObject.get("Option b");
            String c = (String) quizObject.get("Option c");
            String d = (String) quizObject.get("Option d");
            System.out.println(name);
            System.out.println(a);
            System.out.println(b);
            System.out.println(c);
            System.out.println(d);

            System.out.println("Enter your answer: ");
            Scanner sc = new Scanner(System.in);
            String student_ans = sc.next();
            String ans = (String) quizObject.get("answer");

            if (student_ans.equals(ans)) {
                System.out.println("You entered correct answer!");
                point++;
            } else {
                System.out.println("Your answer is incorrect!");
            }
        }
        System.out.println("Result: You got total " + point + " out of 5");
    }
}
