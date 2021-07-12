import java.util.Random;

public class Noise
{
    private static GridVector randomGridVector(int ix, int iy)
    {
        float random = (float) (2920.0F * Math.sin(ix * 21942.0F + iy * 171324.0F + 8912.0F) * Math.cos(ix * 23157.0F * iy * 217832.0F + 9758.0F));
        return new GridVector((float)Math.cos(random), (float)Math.sin(random));
    }

    private static float dotProduct(int ix, int iy, float x, float y)
    {
        GridVector gradient = randomGridVector(ix, iy);

        float dx = x - (float)ix;
        float dy = y - (float)iy;

        return (dx * gradient.x) + (dy * gradient.y);
    }

    private static float perlinNoise(float x, float y)
    {
        float perlin;

        int x0 = (int)x;
        int x1 = x0 + 1;
        int y0 = (int)y;
        int y1 = y0 + 1;

        float sx = x - (float)x0;
        float sy = y - (float)y0;

        float n0, n1, ix0, ix1;

        n0 = dotProduct(x0, y0, x, y);
        n1 = dotProduct(x1, y0, x, y);
        ix0 = interpolate(n0, n1, sx);

        n0 = dotProduct(x0, y1, x, y);
        n1 = dotProduct(x1, y1, x, y);
        ix1 = interpolate(n0, n1, sx);

        perlin = interpolate(ix0, ix1, sy);

        return perlin;
    }

    public static float[] generateNoiseMap(int width, int height, float scale)
    {
        float[] map = new float[width * height];

        if (scale <= 0)
        {
            scale = 0.0001F;
        }

        for (int y = 0; y < width; y++)
        {
            for (int x = 0; x < height; x++)
            {
                float sampleX = x / scale;
                float sampleY = y / scale;

                float perlinValue = Noise.perlinNoise(sampleX, sampleY);
                map[x * width + y] = perlinValue;
            }
        }

        return map;
    }

    private static float interpolate(float a0, float a1, float w)
    {
        return (a1 - a0) * ((w * (w * 6.0f - 15.0f) + 10.0f) * w * w * w) + a0;
    }

    public static void main(String[] args)
    {
        Window window = Window.get();
        window.run();
    }

    static class GridVector
    {
        protected float x;
        protected float y;

        public GridVector (float x, float y)
        {
            this.x = x;
            this.y = y;
        }
    }
}
