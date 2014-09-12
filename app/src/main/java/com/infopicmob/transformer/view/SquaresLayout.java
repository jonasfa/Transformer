package com.infopicmob.transformer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.util.Random;

public class SquaresLayout extends FrameLayout {
	public static final int LINES = 12;
	public static final int ROWS = 9;

	private float openAmount;

	private final RectF[][] squares = new RectF[LINES][ROWS];
	private final RectF[][] transformedSquares = new RectF[squares.length][squares[0].length];
	private float[][] itemRandomnessFactors = new float[squares.length][squares[0].length];
	private final Matrix matrix = new Matrix();

	public SquaresLayout(Context context, AttributeSet attrs) {
		super(context, attrs);

		fill(squares);
		fill(transformedSquares);
		generateItemRandomnessFactors();
	}

	private void fill(RectF[][] rects) {
		for (int line = 0; line < rects.length; line++)
			for (int row = 0; row < rects[line].length; row++)
				rects[line][row] = new RectF();
	}

	public float getOpenAmount() {
		return openAmount;
	}

	public void setOpenAmount(float openAmount) {
		this.openAmount = openAmount;
		setVisibility(openAmount == 1 ? GONE : VISIBLE);
		updateTransformedSquares();
		if (openAmount == 0 || openAmount == 1) generateItemRandomnessFactors();

		invalidate();
	}

	private void generateItemRandomnessFactors() {
		for (int line = 0; line < squares.length; line++) {
			for (int row = 0; row < squares[line].length; row++) {
				itemRandomnessFactors[line][row] = (float) (1 + Math.random() * 1.5);
			}
		}

		// keep a non-random factor so the animation duration is respected
		int nonRandomLine = new Random().nextInt(itemRandomnessFactors.length);
		int nonRandomRow = new Random().nextInt(itemRandomnessFactors[nonRandomLine].length);
		itemRandomnessFactors[nonRandomLine][nonRandomRow] = 1;
	}

	private void updateTransformedSquares() {
		for (int line = 0; line < squares.length; line++) {
			for (int row = 0; row < squares[line].length; row++) {
				float itemOpenAmount = Math.min(1, openAmount * itemRandomnessFactors[line][row]);

				transformedSquares[line][row].set(squares[line][row]);
				transformedSquares[line][row].inset(squares[line][row].width() / 2.f * itemOpenAmount, squares[line][row].height() / 2.f * itemOpenAmount);
			}
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		for (int line = 0; line < squares.length; line++) {
			for (int row = 0; row < squares[line].length; row++) {
				squares[line][row].set(row * ((float) w / squares[line].length), line * ((float) h / squares.length), (row + 1) * ((float) w / squares[line].length), (line + 1) * ((float) h / squares.length));
			}
		}

		updateTransformedSquares();
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		if (isInEditMode()) setOpenAmount(0.5f);
	}

	@Override
	public void draw(Canvas canvas) {
		for (int line = 0; line < squares.length; line++) {
			for (int row = 0; row < squares[line].length; row++) {
				canvas.save();

				matrix.setRectToRect(squares[line][row], transformedSquares[line][row], Matrix.ScaleToFit.FILL);
				canvas.clipRect(transformedSquares[line][row]);
				canvas.concat(matrix);

				super.draw(canvas);

				canvas.restore();
			}
		}
	}

}