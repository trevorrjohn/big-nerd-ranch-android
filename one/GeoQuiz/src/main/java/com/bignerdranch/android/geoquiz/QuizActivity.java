package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {
  private Button mTrueButton;
  private Button mFalseButton;
  private Button mNextButton;
  private Button mPreviousButton;
  private TextView mQuestionTextView;
  private int mCurrentIndex = 0;
  private TrueFalse[] mQuestionBank = new TrueFalse[] {
          new TrueFalse(R.string.question_africa, true),
          new TrueFalse(R.string.question_americas, false),
          new TrueFalse(R.string.question_asia, true),
          new TrueFalse(R.string.question_mideast, false),
          new TrueFalse(R.string.question_oceans, true)
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);

    mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
    mTrueButton = (Button) findViewById(R.id.true_button);
    mFalseButton = (Button) findViewById(R.id.false_button);
    updateQuestion();

    mQuestionTextView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        updateQuestion();
      }
    });
    mTrueButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        checkAnswer(true);
      }
    });

    mFalseButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        checkAnswer(false);
      }
    });

    mNextButton = (Button) findViewById(R.id.next_button);
    mNextButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        updateQuestion();
      }
    });

    mPreviousButton = (Button) findViewById(R.id.previous_button);
    mPreviousButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mCurrentIndex = (mCurrentIndex == 0 ? mQuestionBank.length :  mCurrentIndex) - 1;
        updateQuestion();
      }
    }
    );
  }

  private void updateQuestion() {
    if(mCurrentIndex == -1) { alertError(); }
    int question = mQuestionBank[mCurrentIndex].getQuestion();
    mQuestionTextView.setText(question);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.quiz, menu);
    return true;
  }

  private void checkAnswer(boolean userPressedTrue) {
    if(mCurrentIndex == -1) { alertError(); }
    boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();

    int messageResId;

    if(userPressedTrue == answerIsTrue) {
      messageResId = R.string.correct_toast;
    } else {
      messageResId = R.string.incorrect_toast;
    }

    Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
  }

  private void alertError() {
    Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
  }
}
