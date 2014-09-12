package com.infopicmob.transformer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import java.util.Arrays;

public class CurtainLayout extends FrameLayout {
	private static final int SIDE_BENDS_COUNT = 3;

	private final Matrix matrix = new Matrix();
	private final Interpolator bottomInterpolator = new AccelerateInterpolator();
	/**
	 * First positions close last
	 * Eg: 1, 1, 1, .7, .0
	 */
	private final float[] bendsOpening = new float[SIDE_BENDS_COUNT];
	private final float[] interpolatedBendsOpening = new float[SIDE_BENDS_COUNT];

	private View curtainView;
	private float openAmount;

	public CurtainLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void addView(View child, int index, ViewGroup.LayoutParams params) {
		super.addView(child, index, params);

		curtainView = child;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		if (isInEditMode()) curtainView.setAlpha(0.5f);
	}

	@SuppressWarnings("UnusedDeclaration")
	public float getOpenAmount() {
		return openAmount;
	}

	@SuppressWarnings("UnusedDeclaration")
	public void setOpenAmount(float openAmount) {
		this.openAmount = openAmount;
//		curtainView.setVisibility(openAmount == 1 ? GONE : VISIBLE);

		Log.d("openAmount", String.valueOf(openAmount));

		invalidate();
	}

	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		if (child == curtainView) {
			updateBendsOpenness(bendsOpening, openAmount);
			updateBendsOpenness(interpolatedBendsOpening, bottomInterpolator.getInterpolation(openAmount));

			final float openBendWidth = getWidth() / 2 / (float) SIDE_BENDS_COUNT;
			final float closedBendWidth = getWidth() / 2 / (float) SIDE_BENDS_COUNT / 10;

			Log.d("bends", Arrays.toString(bendsOpening));

			boolean result = false;

			float topLeft = 0;
			float bottomLeft = 0;
			for (int i = 0; i < SIDE_BENDS_COUNT; i++) {
				canvas.save();

				float topWidth = closedBendWidth + (openBendWidth - closedBendWidth) * bendsOpening[i];
				float bottomWidth = closedBendWidth + (openBendWidth - closedBendWidth) * interpolatedBendsOpening[i];
//				matrix.setRectToRect(new RectF(openBendWidth * i, 0, openBendWidth * i + openBendWidth, getHeight()), new RectF(topLeft, 0, topLeft + topWidth, getHeight()), Matrix.ScaleToFit.FILL);
				float[] src = {
						openBendWidth * i, 0,
						openBendWidth * i + openBendWidth, 0,
						openBendWidth * i, getHeight(),
						openBendWidth * i + openBendWidth, getHeight()
				};
				float[] dst = {
						topLeft, 0,
						topLeft + topWidth, 0,
						topLeft, getHeight(),
						topLeft + topWidth, getHeight()
				};
				matrix.setPolyToPoly(src, 0,
						dst, 0, 4);
				canvas.concat(matrix);

				Path path = new Path();
				path.moveTo(dst[0], dst[1]);
				path.lineTo(dst[2], dst[3]);
				path.lineTo(dst[6], dst[7]);
				path.lineTo(dst[4], dst[5]);
//				canvas.pathclipPath(path);
//						canvas.

				result |= super.drawChild(canvas, child, drawingTime);

				canvas.restore();
				topLeft += topWidth;
				bottomLeft += bottomWidth;
			}

			float right = getWidth();
			for (int i = 0; i < SIDE_BENDS_COUNT; i++) {
				canvas.save();

				float width = closedBendWidth + (openBendWidth - closedBendWidth) * bendsOpening[SIDE_BENDS_COUNT - 1 - i];
				canvas.clipRect(right - width, 0, right, getHeight());
				matrix.setRectToRect(new RectF(getWidth() - openBendWidth * (i + 1), 0, getWidth() - openBendWidth * i, getHeight()), new RectF(right - width, 0, right, getHeight()), Matrix.ScaleToFit.FILL);
				canvas.concat(matrix);
				result |= super.drawChild(canvas, child, drawingTime);

				canvas.restore();
				right -= width;
			}

			return result;
		} else {
			return super.drawChild(canvas, child, drawingTime);
		}
	}

	private static void updateBendsOpenness(float[] bendsOpening, float openAmount) {
		int fullyOpenedBends = (int) (SIDE_BENDS_COUNT * (1 - openAmount));
		if (fullyOpenedBends >= 1) Arrays.fill(bendsOpening, 0, fullyOpenedBends, 1f);

		int fullyClosedBends = (int) (SIDE_BENDS_COUNT - SIDE_BENDS_COUNT * (1 - openAmount));
		if (fullyClosedBends >= 1)
			Arrays.fill(bendsOpening, SIDE_BENDS_COUNT - fullyClosedBends, SIDE_BENDS_COUNT, 0f);

		if (fullyOpenedBends + fullyClosedBends < SIDE_BENDS_COUNT)
			bendsOpening[fullyOpenedBends] = SIDE_BENDS_COUNT * (1 - openAmount) - (int) (SIDE_BENDS_COUNT * (1 - openAmount));
	}
}