package object;

/**
 */
import android.graphics.Color;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import hieuxinhe.notificationappication.R;

public class BuilderManager {
    private static int[] imageResources = new int[]{
        R.drawable.logout_icon,
        R.drawable.clicktoweb
};
    private static int imageResourceIndex = 0;
    public static int getImageResource() {
        if (imageResourceIndex >= imageResources.length) imageResourceIndex = 0;
        // return imageResources[imageResourceIndex++];
        return imageResources[0];
    }
    public static int getImageResource2() {
        return imageResources[1];
    }
    static SimpleCircleButton.Builder getSimpleCircleButtonBuilder() {
        return new SimpleCircleButton.Builder()
                .normalImageRes(getImageResource());
    }
    static TextInsideCircleButton.Builder getTextInsideCircleButtonBuilder() {
        return new TextInsideCircleButton.Builder()
                .normalImageRes(getImageResource())
                .normalTextRes(R.string.text_inside_circle_button_text_normal);
    }
    static TextInsideCircleButton.Builder getTextInsideCircleButtonBuilderWithDifferentPieceColor() {
        return new TextInsideCircleButton.Builder()
                .normalImageRes(getImageResource())
                .normalTextRes(R.string.text_inside_circle_button_text_normal)
                .pieceColor(Color.WHITE);
    }
    static TextOutsideCircleButton.Builder getTextOutsideCircleButtonBuilder() {
        return new TextOutsideCircleButton.Builder()
                .normalImageRes(getImageResource())
                .normalTextRes(R.string.text_outside_circle_button_text_normal);
    }
    static TextOutsideCircleButton.Builder getTextOutsideCircleButtonBuilderWithDifferentPieceColor() {
        return new TextOutsideCircleButton.Builder()
                .normalImageRes(getImageResource())
                .normalTextRes(R.string.text_outside_circle_button_text_normal)
                .pieceColor(Color.WHITE);
    }
    public static HamButton.Builder getHamButtonBuilder1()                                     {
        return new HamButton.Builder()
                .normalImageRes(getImageResource())
                .normalTextRes(R.string.text_ham_button_text_normal)
                .subNormalTextRes(R.string.text_ham_button_sub_text_normal).listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {

                    }
                });
    }
    static HamButton.Builder getHamButtonBuilderWithDifferentPieceColor() {
        return new HamButton.Builder()
                .normalImageRes(getImageResource())
                .normalTextRes(R.string.text_ham_button_text_normal)
                .subNormalTextRes(R.string.text_ham_button_sub_text_normal)
                .pieceColor(Color.WHITE);
    }
    private static BuilderManager ourInstance = new BuilderManager();
    public static BuilderManager getInstance() {
        return ourInstance;
    }
    private BuilderManager() {
    }

}