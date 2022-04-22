# Activities and Intentions

## Exercise 1
Getting familiar with activites and intentions.
#### Task A
Creating a simple Toast to display a random number.
##### **`RandomActivity.java`**
```java
int random_number = (int)(Math.random() * 100);
Toast toast = Toast.makeText(this, "Toast with random number:  " + random_number, Toast.LENGTH_SHORT);
toast.show();
```
When launching the app you get this: (Note the Toast at the bottom displaying the random number)  
<img src="./Exercise%201/images/taskA.png" alt="Toast demo" width="200"/>
<br />
<br />

#### Task B
Sending the number boundary from the `Main` activity to the `Random` activity with an Intent:
##### `MainActivity.java`
```java
int number_boundary = 500;
Intent intent = new Intent("A2.Exercise1.RandomActivity");
intent.putExtra("number-boundary", number_boundary);
startActivityForResult(intent, REQUEST_CODE_getRandomNumber);
```
Im also displaying this boundary in a textView on the `Main` activity.
```java
TextView textView = findViewById(R.id.number_boundary_text);
String text = "Number boundary: " + number_boundary);
textView.setText(text);
```
##### `RandomActivity.java`
```java
int number_boundary = getIntent().getIntExtra("number-boundary", 100);
```
<br />
<br />

#### Task C and D
Creating random number in the Random activity based on the boundary recieved. Then sends the result back to the Main activity and fishes.
##### `RandomActivity.java`
```java
int random_number = (int) (Math.random() * number_boundary);

Intent intent = new Intent();
intent.putExtra("random_number", random_number);
setResult(RESULT_OK, intent);
finish();
```

Adding intent filter to the manifest file
##### `AndroidManifest.xml`
```xml
<activity android:name=".RandomActivity">
    <intent-filter>
        <action android:name="A2.Exercise1.RandomActivity" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
</activity>
```
<br />
<br />

#### Task E
Testing wether Task B, C and D works by receiving the result then displaying it in a TextView
```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE_getRandomNumber && resultCode == RESULT_OK) {
        TextView textView = findViewById(R.id.default_textView);
        String text = "Random number from other activity: " + data.getIntExtra("random_number", -1);
        textView.setText(text);
    }
}
```
<img src="./Exercise%201/images/taskE.png" alt="Demo Exercise 1" width="200"/>
<br />
<br />



## Exercise 2
Creating a calculation app to exercise adding and multiplying two numbers.
#### Task A
```xml
<resources>
    <string name="app_name">02 - Activities and Intentions - Exercise 2</string>
    <string name="add">Add</string>
    <string name="multiply">Multiply</string>
    <string name="correct">Correct!</string>
    <string name="incorrect">Wrong! The correct answer was: </string>
    <string name="answer">Answer: </string>
    <string name="boundary_text">Number boundary: </string>
</resources>
```
<br />
<br />

#### Task B
Creating the layout:  
<img src="./Exercise%202/images/taskB.png" alt="Layout" width="200"/>
<br />
<br />

#### Task C
Adding onClick methods for the buttons:
##### **`MainActivity.java`**
```java
final int ADDITION = 1;
final int MULTIPLICATION = 2;

public void onAdditionClicked(View view) {
        checkCalculation(ADDITION);
    }

public void onMultiplicationClicked(View view) {
    checkCalculation(MULTIPLICATION);
}

private void checkCalculation(int calculationType) {
    int number1 = -1;
    int number2 = -1;
    int answer = -1;
    TextView number1TextView = findViewById(R.id.number1);
    TextView number2TextView = findViewById(R.id.number2);
    EditText answerEditText = findViewById(R.id.number_answer);
    try {
        number1 = Integer.parseInt(number1TextView.getText().toString());
        number2 = Integer.parseInt(number2TextView.getText().toString());
        answer = Integer.parseInt(answerEditText.getText().toString());
    } catch (Exception e) {
        Toast toast = Toast.makeText(this, "Error: invalid input", Toast.LENGTH_LONG);
        toast.show();
    }

    int correctAnswer;
    switch (calculationType) {
        case ADDITION:
            correctAnswer = number1 + number2;
            break;
        case MULTIPLICATION:
            correctAnswer = number1 * number2;
            break;
        default:
            throw new IllegalStateException("Unexpected value: " + calculationType);
    }

    String toast_message;
    if (answer == correctAnswer) {
        toast_message = this.getString(R.string.correct);
    } else {
        toast_message = this.getString(R.string.incorrect) + correctAnswer;
    }

    Toast toast = Toast.makeText(this, toast_message, Toast.LENGTH_SHORT);
    toast.show();
}

```
<br />
<br />

#### Task D
Utelising the `RandomActivity` from exercise 1 to create new random numbers after each calculation by adding:
##### **`MainActivity.java`**
```java
Intent intent = new Intent("A2.Exercise2.RandomActivity");
intent.putExtra("number-boundary", number_boundary);
startActivityForResult(intent, 1);
startActivityForResult(intent, 2);
```
After displaying the Toast, and we also need to add:
##### **`MainActivity.java`**
```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode != RESULT_OK) return;
    String random_number = Integer.toString(data.getIntExtra("random-number", -1));
    if (requestCode == 1) {
        TextView textView = findViewById(R.id.number1);
        textView.setText(random_number);
    }
    if (requestCode == 2) {
        TextView textView = findViewById(R.id.number2);
        textView.setText(random_number);
    }
}
```
To handle the result from the intents we are sending, and update the numbers on the screen.

## Final Result

<img src="./Exercise%202/images/demo.gif" alt="Toast demo" width="200"/>
