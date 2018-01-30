package ad.play.OnBoarding;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ad.play.R;


public class OnBoardingFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section-icon";
    private static final String ARG_SECTION_COLOR = "section-color";
    private static final String ARG_SECTION_TEXT = "section-text";


    public static OnBoardingFragment newInstance(int color, int icon, int text) {
        OnBoardingFragment fragment = new OnBoardingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, icon);
        args.putInt(ARG_SECTION_COLOR, color);
        args.putInt(ARG_SECTION_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_intro, container, false);
        rootView.setBackgroundColor(
                ContextCompat.getColor(getContext(), getArguments().getInt(ARG_SECTION_COLOR)));
        ((TextView) rootView.findViewById(R.id.textView)).setText(getArguments().getInt(ARG_SECTION_TEXT));
        ImageView image = (ImageView) rootView.findViewById(R.id.iv_icon);
        Picasso.with(getContext()).load(getArguments().getInt(ARG_SECTION_NUMBER)).into(image);
        return rootView;
    }
}



