package com.sameh.taskjava.ui.company;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.gamify.space.Gamify;
import com.gamify.space.GamifyError;
import com.sameh.taskjava.R;
import com.sameh.taskjava.data.Company;
import com.sameh.taskjava.databinding.FragmentCompanyBinding;

public class CompanyFragment extends Fragment {

    private static final String OKSPIN_APP_KEY = "O6Z1x4LpMl6jKSmq1GJdMhZZB999Otk3";
    private static final String OKSPIN_PLACEMENT = "10882";

    private FragmentCompanyBinding binding;

    private NavController navController;
    private NavDestination currentDestination;

    Company company;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompanyBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(requireView());
        currentDestination = navController.getCurrentDestination();

        assert getArguments() != null;
        company = getArguments().getParcelable("company");

        setCompanyData();
        setActions();

        switch (company.getCompany()) {
            case OKSpin:
                setListenerToOkspinSDK();
                initOkspinSDK();
                break;

            case Roulax:
                break;
        }
    }

    private void setCompanyData() {
        binding.tvCompanyName.setText(company.getName());
        binding.imgCompany.setImageResource(company.getLogo());
        binding.imgCoverCompany.setImageResource(company.getCover());
        binding.tvCompanySubtitle.setText(company.getSubTitle());
        binding.tvCompanyOverview.setText(company.getOverview());
    }

    private void setActions() {
        binding.tvVisitCompanyWebsite.setOnClickListener(v -> openLink(company.getWebsite()));
        binding.btnBack.setOnClickListener(v -> backToHomeFragment());
    }

    private void initOkspinSDK() {
        showProgress(true);
        Gamify.initSDK(OKSPIN_APP_KEY);
    }

    private void showPlacementCreative(String placement) {
        View iconView = Gamify.showIcon(placement);
        if (iconView != null) {
            if (iconView.getParent() != null) {
                ViewGroup viewGroup = (ViewGroup) iconView.getParent();
                viewGroup.removeView(iconView);
            }
            iconView.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
            binding.linearCompanyIcon.addView(iconView);
        }
    }

    private void setListenerToOkspinSDK() {
        Gamify.setListener(new Gamify.GamifyListener() {
            @Override
            public void onInitSuccess() {
                // Invoked when SDK init success
                showLogD("onInitSuccess");
                Gamify.loadIcon(OKSPIN_PLACEMENT);
            }

            @Override
            public void onInitFailed(GamifyError error) {
                // Invoked when SDK init failed
                showLogD("onInitFailed" + error.getMsg());
                showToast("onInitFailed" + error.getMsg());
            }

            @Override
            public void onIconReady(String placement) {
                // Placement load success
                showLogD("onIconReady");
                showPlacementCreative(placement);
                showProgress(false);
            }

            @Override
            public void onIconLoadFailed(String placement, GamifyError error) {
                // Placement load failed
                showLogD("onInitFailed placement" + placement);
                showToast("onInitFailed error" + error.getMsg());
            }

            @Override
            public void onIconShowFailed(String placementId, GamifyError error) {
                // Placement show failed
                showLogD("onIconShowFailed placementId" + placementId);
                showLogD("onIconShowFailed error" + error.getMsg());
                showToast("onIconShowFailed error" + error.getMsg());
                showProgress(false);
            }

            @Override
            public void onIconClick(String placement) {
                // Invoked when user click Placement
                showLogD("onIconClick placement" + placement);
            }

            @Override
            public void onInteractiveOpen(String placement) {
                // Invoked when GSpace - Interactive Ads page opened
                showLogD("onInteractiveOpen placement" + placement);
            }

            @Override
            public void onInteractiveOpenFailed(String placementId, GamifyError error) {
                // Invoked when GSpace - Interactive Ads page open failed
                showLogD("onInteractiveOpenFailed placementId" + placementId);
                showLogD("onInteractiveOpenFailed error" + error.getMsg());
                showToast("onInteractiveOpenFailed error" + error.getMsg());
            }

            @Override
            public void onInteractiveClose(String placement) {
                // Invoked when GSpace - Interactive Ads page closed
                showLogD("onInteractiveClose placement" + placement);
            }

            @Override
            public void onOfferWallOpen(String placementId) {
                // Invoked when GSpace - Interactive Wall page opened
                showLogD("onOfferWallOpen placementId" + placementId);
            }

            @Override
            public void onOfferWallOpenFailed(String placementId, GamifyError error) {
                // Invoked when GSpace - Interactive Wall page open failed
                showLogD("onOfferWallOpenFailed placementId" + placementId);
                showLogD("onOfferWallOpenFailed error" + error.getMsg());
                showToast("onOfferWallOpenFailed error" + error.getMsg());
            }

            @Override
            public void onOfferWallClose(String placementId) {
                // Invoked when GSpace - Interactive Wall page closed
                showLogD("onOfferWallClose placementId" + placementId);
            }

            @Override
            public void onGSpaceOpen(String placementId) {
                // Invoked when GSpace opened
                showLogD("onGSpaceOpen placementId" + placementId);
            }

            @Override
            public void onGSpaceOpenFailed(String placementId, GamifyError error) {
                // Invoked when GSpace open failed
                showLogD("onGSpaceOpenFailed placementId" + placementId);
                showLogD("onGSpaceOpenFailed error" + error.getMsg());
                showToast("onGSpaceOpenFailed error" + error.getMsg());
            }

            @Override
            public void onGSpaceClose(String placementId) {
                // Invoked when GSpace closed
                showLogD("onGSpaceClose placementId" + placementId);
            }

            @Override
            public void onUserInteraction(String placementId, String interaction) {
                showLogD("onUserInteraction placementId" + placementId);
                showLogD("onUserInteraction interaction" + interaction);
            }
        });
    }

    private void showProgress(boolean show) {
        if (show) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    private void showLogD(String text) {
        Log.d("debuggingTAG", text);
    }

    private void showToast(String text) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show();
    }

    private void backToHomeFragment() {
        int currentDestinationId = currentDestination != null ? currentDestination.getId() : -1;

        if (currentDestinationId == R.id.companyFragment) {
            navController.popBackStack();
        }
    }

    private void openLink(String url) {
        try {
            String mUrl = url;
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                mUrl = "http://" + url;
            }
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mUrl));
            startActivity(browserIntent);
        } catch (Exception e) {
            showLogD(e.getMessage());
            showToast(e.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}