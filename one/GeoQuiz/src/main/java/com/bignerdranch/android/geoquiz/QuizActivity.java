package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {

  private static final String KEY_INDEX = "index";

  private Button mTrueButton;
  private Button mFalseButton;
  private Button mCheatButton;
  private ImageButton mNextButton;
  private ImageButton mPreviousButton;
  private TextView mQuestionTextView;
  private int mCurrentIndex = 0;
  private boolean mIsCheater;
  private TrueFalse[] mQuestionBank = new TrueFalse[] {
          new TrueFalse(R.string.question_oceans, true),
          new TrueFalse(R.string.question_mideast, false),
          new TrueFalse(R.string.question_africa, true),
          new TrueFalse(R.string.question_americas, false),
          new TrueFalse(R.string.question_asia, true)
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);

    mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
    mTrueButton = (Button) findViewById(R.id.true_button);
    mFalseButton = (Button) findViewById(R.id.false_button);

    if(savedInstanceState != null) {
      mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
    }

    updateQuestion();

    mCheatButton = (Button) findViewById(R.id.cheat_button);
    mCheatButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
        intent.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, mQuestionBank[mCurrentIndex].isTrueQuestion());
        startActivityForResult(intent, 0);
      }
    });

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

    mNextButton = (ImageButton) findViewById(R.id.next_button);
    mNextButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        mIsCheater = false;
        updateQuestion();
      }
    });

    mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
    mPreviousButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mCurrentIndex = (mCurrentIndex == 0 ? mQuestionBank.length :  mCurrentIndex) - 1;
        updateQuestion();
      }
    });
  }

  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    super.onSaveInstanceState(savedInstanceState);

    savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.quiz, menu);
    return true;
  }

  @Override
  protected void onActivityResult(int requestCode, int resultcode, Intent data) {
    if(data == null) {
      return;
    }
    mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
  }

  private void checkAnswer(boolean userPressedTrue) {
    boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();

    int messageResId = 0;
    if(mIsCheater) {
      messageResId = R.string.judgment_toast;
    } else {
      messageResId = (userPressedTrue == answerIsTrue) ?
              R.string.correct_toast :
              R.string.incorrect_toast;
    }
    Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
  }

  private void updateQuestion() {
    int question = mQuestionBank[mCurrentIndex].getQuestion();
    mQuestionTextView.setText(question);
  }
}
