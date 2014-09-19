package com.infopicmob.transformer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SimpleFoldView extends ImageView {
	private float folding;

	public SimpleFoldView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * @param folding De 0 (totalmente aberta) até 1 (totalmente dobrada)
	 */
	public void setFolding(float folding) {
		this.folding = Math.max(0, Math.min(folding, 1));
		invalidate();
	}

	Matrix matrix = new Matrix();

	public void draw(Canvas canvas) {
		canvas.save();

		canvas.scale(1, 1 - folding, 0, getHeight() / 2);

		float maxFoldWidth = getHeight() / 4;
		float currentFoldWidth = maxFoldWidth * folding;

		/* top half */

		canvas.save();
		float[] topOriginalCoordinates = {
				0, 0, /*              */ getWidth(), 0,
				0, getHeight() / 2, /**/ getWidth(), getHeight() / 2
		};
		float[] topTransformedCoordinates = {
				0, 0, /*                             */ getWidth(), 0,
				currentFoldWidth, getHeight() / 2, /**/ getWidth() - currentFoldWidth, getHeight() / 2
		};

		matrix.setPolyToPoly(topOriginalCoordinates, 0, topTransformedCoordinates, 0, 4);
		canvas.concat(matrix);

		canvas.clipRect(0, 0, getWidth(), getHeight() / 2);

		super.draw(canvas);
		// top shadow
		canvas.drawColor(Color.argb((int) (70 * folding), 0, 0, 0));

		canvas.restore();

		/* bottom half */

		canvas.save();
		float[] bottomOriginalCoordinates = {
				0, getHeight() / 2, /*              */ getWidth(), getHeight() / 2,
				0, getHeight(), /**/ getWidth(), getHeight()
		};
		float[] bottomTransformedCoordinates = {
				currentFoldWidth, getHeight() / 2, /**/ getWidth() - currentFoldWidth, getHeight() / 2,
				0, getHeight(), /**/ getWidth(), getHeight()
		};

		matrix.setPolyToPoly(bottomOriginalCoordinates, 0, bottomTransformedCoordinates, 0, 4);
		canvas.concat(matrix);

		canvas.clipRect(0, getHeight() / 2, getWidth(), getHeight());

		super.draw(canvas);
		canvas.restore();
		//


		canvas.restore();
	}

}