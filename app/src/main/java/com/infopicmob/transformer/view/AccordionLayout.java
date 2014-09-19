package com.infopicmob.transformer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class AccordionLayout extends FrameLayout {
	public static final int BENDS_COUNT = 4;

	private final Drawable deepenShade = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#22000000"), Color.parseColor("#55000000")});
	private final Drawable shallowShade = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#33000000"), Color.parseColor("#00000000")});
	private final Matrix matrix = new Matrix();
	private final float[] originalPoly = new float[8];
	private final float[] transformedPoly = new float[originalPoly.length];

	private float folding;

	public AccordionLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setFolding(float folding) {
		this.folding = Math.max(0, Math.min(folding, 1));
		invalidate();
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		if (folding == 0) {
			super.dispatchDraw(canvas);
			return;
		}

		canvas.save();
		matrix.setScale(1, 1 - folding, 0, getHeight());
		canvas.concat(matrix);

		int parts = BENDS_COUNT * 2;
		int partHeight = getHeight() / parts;

		originalPoly[0] = originalPoly[4] = 0;
		originalPoly[2] = originalPoly[6] = getWidth();

		float inPadding = partHeight / 2;

		for (int part = 0; part < parts; part++) {
			canvas.save();

			boolean deepening = part % 2 == 1;

			int top = part * partHeight;
			originalPoly[1] = top;
			originalPoly[3] = originalPoly[1];
			originalPoly[5] = originalPoly[1] + partHeight;
			originalPoly[7] = originalPoly[5];

			System.arraycopy(originalPoly, 0, transformedPoly, 0, originalPoly.length);
			if (deepening) {
				transformedPoly[4] = originalPoly[4] + inPadding * folding;
				transformedPoly[6] = originalPoly[6] - inPadding * folding;
			} else {
				transformedPoly[0] = originalPoly[0] + inPadding * folding;
				transformedPoly[2] = originalPoly[2] - inPadding * folding;
			}

			matrix.setPolyToPoly(originalPoly, 0, transformedPoly, 0, 4);

			canvas.concat(matrix);
			canvas.clipRect(0, top, getWidth(), top + partHeight);

			super.dispatchDraw(canvas);

			Drawable shade = deepening ? deepenShade : shallowShade;
			shade.setBounds(0, top, getWidth(), top + partHeight);
			shade.setAlpha((int) (255 * folding));
			shade.draw(canvas);

			canvas.restore();
		}
		canvas.restore();
	}
}