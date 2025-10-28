package fr.ubordeaux.ao.mazing.api;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

class ImageLoader {

	private static ImageLoader instance;

	private ImageLoader() {
	}

	public static ImageLoader getInstance() {
		if (instance == null) {
			instance = new ImageLoader();
		}
		return instance;
	}

	public static BufferedImage loadImage(String path) {
		try {
			// 1️⃣ Essayer comme ressource du classpath
			var resource = ImageLoader.class.getResource(path);
			if (resource != null) {
				return ImageIO.read(resource);
			}

			// 2️⃣ Sinon, essayer comme fichier sur disque
			File file = new File(path);
			if (file.exists()) {
				return ImageIO.read(file);
			}
			System.err.println("Image not found: " + path + " (neither in resources nor on disk)");

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage loadImageIfExists(String path) {
		try {
			java.net.URL resource = ImageLoader.class.getResource(path);
			if (resource == null) {
				return null; // le fichier n'existe pas
			}
			return ImageIO.read(resource);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Charge une série d'images selon le pattern :
	 * crusader_run_x00Y.png
	 * x = 1,3,5,7 (directions)
	 * y = 00..16 (frames)
	 */
	public static BufferedImage[] loadAnimations(int numFrames, int direction, String prefix, float scale, Color color) {
		BufferedImage[] animations = new BufferedImage[numFrames];
		final int MAX_PIXELS = 65536; // définition max : largeur * hauteur

		for (int frame = 0; frame < numFrames; frame++) {
			String frameNumber = String.format("%04d", frame);
			String directionNumber = String.format("%01d", direction);
			String path = String.format("%s%s%s.png", prefix, directionNumber, frameNumber);

			// System.out.println(path);

			BufferedImage img = loadImage(path);
			if (img != null) {
				int originalWidth = img.getWidth();
				int originalHeight = img.getHeight();

				// Application du scale
				int newWidth = (int) (originalWidth / scale);
				int newHeight = (int) (originalHeight / scale);

				// Limite de définition (nombre total de pixels)
				long pixels = (long) newWidth * newHeight;
				if (pixels > MAX_PIXELS) {
					double ratio = Math.sqrt((double) MAX_PIXELS / pixels);
					newWidth = Math.max(1, (int) Math.round(newWidth * ratio));
					newHeight = Math.max(1, (int) Math.round(newHeight * ratio));
				}

				BufferedImage scaled = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2d = scaled.createGraphics();
				g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				g2d.drawImage(img, 0, 0, newWidth, newHeight, null);
				g2d.dispose();

				if (color != null) {
					scaled = tintByLuminosity(scaled, color);
				}

				animations[frame] = scaled;
			}
		}

		return animations;
	}

	/**
	 * Charge une série d'images selon le pattern :
	 * crusader_run_x00Y.png
	 * x = 1,3,5,7 (directions)
	 * y = 00..16 (frames)
	 */
	public static BufferedImage[] loadTurnAnimations(int numFrames, String prefix, float scale, boolean left) {
		BufferedImage[] animations = new BufferedImage[numFrames];
		final int MAX_PIXELS = 65536; // définition max : largeur * hauteur

		for (int frame = 0; frame < numFrames; frame++) {
			Direction d = Direction.values()[frame % Direction.values().length];
			int direction = d.ordinal();

			String frameNumber = String.format("%04d", frame);
			String directionNumber = String.format("%01d", direction);
			String path = String.format("%s%s%s.png", prefix, directionNumber, frameNumber);

			BufferedImage img = loadImage(path);
			if (img != null) {
				int originalWidth = img.getWidth();
				int originalHeight = img.getHeight();

				int newWidth = (int) (originalWidth / scale);
				int newHeight = (int) (originalHeight / scale);

				// Limite de définition (nombre total de pixels)
				long pixels = (long) newWidth * newHeight;
				if (pixels > MAX_PIXELS) {
					double ratio = Math.sqrt((double) MAX_PIXELS / pixels);
					newWidth = Math.max(1, (int) Math.round(newWidth * ratio));
					newHeight = Math.max(1, (int) Math.round(newHeight * ratio));
				}

				BufferedImage scaled = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2d = scaled.createGraphics();
				g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				g2d.drawImage(img, 0, 0, newWidth, newHeight, null);
				g2d.dispose();

				// Sens d’affichage selon le paramètre "left"
				animations[left ? frame : numFrames - frame - 1] = scaled;
			}
		}

		return animations;
	}

	
    /**
	 * Mélange une couleur sur tous les pixels opaques, proportionnellement à leur
	 * luminosité.
	 */
	private static BufferedImage tintByLuminosity(BufferedImage img, Color targetColor) {
		int width = img.getWidth();
		int height = img.getHeight();
		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		int rT = targetColor.getRed();
		int gT = targetColor.getGreen();
		int bT = targetColor.getBlue();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int argb = img.getRGB(x, y);
				int alpha = (argb >> 24) & 0xff;

				// Ne jamais toucher les pixels totalement transparents
				if (alpha == 0) {
					result.setRGB(x, y, argb);
					continue;
				}

				int red = (argb >> 16) & 0xff;
				int green = (argb >> 8) & 0xff;
				int blue = argb & 0xff;

				// Luminosité relative
				float lum = (0.299f * red + 0.587f * green + 0.114f * blue) / 255f;

				// On applique la couleur proportionnellement à la luminosité
				float factor = lum; // 0 (noir) -> 1 (blanc)
				red = (int) (red * (1 - factor) + rT * factor);
				green = (int) (green * (1 - factor) + gT * factor);
				blue = (int) (blue * (1 - factor) + bT * factor);

				red = Math.min(255, Math.max(0, red));
				green = Math.min(255, Math.max(0, green));
				blue = Math.min(255, Math.max(0, blue));

				result.setRGB(x, y, (alpha << 24) | (red << 16) | (green << 8) | blue);
			}
		}

		return result;
	}


}