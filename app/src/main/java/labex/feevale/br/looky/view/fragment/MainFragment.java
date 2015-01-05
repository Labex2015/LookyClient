package labex.feevale.br.looky.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.controller.LoadRequestHelpFragment;
import labex.feevale.br.looky.service.GetKnowledges;

/**
 * Created by PabloGilvan on 01/01/2015.
 */
public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ImageButton searchButton = (ImageButton) view.findViewById(R.id.menuPrincipalSearch);
        searchButton.setOnClickListener(loadSearchHelpFragment());
        ImageButton profileButton = (ImageButton) view.findViewById(R.id.menuPrincipalProfile);
        profileButton.setOnClickListener(loadProfileFragment());
        ImageButton chatButton = (ImageButton) view.findViewById(R.id.menuPrincipalChat);

        return view;
    }

    private View.OnClickListener loadProfileFragment() {
          return new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  new GetKnowledges(getActivity()).execute();
              }
          };
    }

    private View.OnClickListener loadSearchHelpFragment() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new LoadRequestHelpFragment("Carregando tela de ajuda!", getActivity()).execute();
                }
            };
    }
}
