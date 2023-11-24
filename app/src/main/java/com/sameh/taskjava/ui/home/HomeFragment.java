package com.sameh.taskjava.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.sameh.taskjava.R;
import com.sameh.taskjava.data.Companies;
import com.sameh.taskjava.data.Company;
import com.sameh.taskjava.databinding.FragmentHomeBinding;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment implements OnCompanyClickListener {

    private FragmentHomeBinding binding;

    private CompaniesAdapter companiesAdapter;

    private NavController navController;
    private NavDestination currentDestination;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(requireView());
        currentDestination = navController.getCurrentDestination();
        companiesAdapter = new CompaniesAdapter(this);

        setCompaniesData();
    }

    private void setCompaniesData() {
        ArrayList<Company> companies = new ArrayList<>();

        Company oKSpin = new Company(
                1,
                "OKSpin",
                "Gamify your brand.",
                getString(R.string.okspin_overview),
                "https://okspin.tech/",
                R.drawable.okspin_logo,
                R.drawable.okspin_cover,
                Companies.OKSpin
        );
        companies.add(oKSpin);

        Company roulax = new Company(
                2,
                "Roulax",
                "Global efficient monetization and advertising platform.",
                getString(R.string.roulax_overview),
                "https://www.roulax.io/",
                R.drawable.roulax_logo,
                R.drawable.roulax_cover,
                Companies.Roulax
        );
        companies.add(roulax);

        companiesAdapter.setData(companies);
        initViewPagerList();
    }

    private void initViewPagerList() {
        binding.viewPager2.setAdapter(companiesAdapter);
        initIndicators();
        subscribeToViewPager();
    }

    private void subscribeToViewPager() {
        binding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                binding.indicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                binding.indicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // empty
            }
        });
    }

    private void initIndicators() {
        int greyColor = ContextCompat.getColor(requireContext(), R.color.greyColor);
        int blackColor = ContextCompat.getColor(requireContext(), R.color.black);

        binding.indicator.setSliderColor(greyColor, blackColor);
        binding.indicator.setSliderWidth(40F);
        binding.indicator.setSliderHeight(10f);
        binding.indicator.setCheckedColor(blackColor);
        binding.indicator.setNormalColor(greyColor);
        binding.indicator.setSlideMode(IndicatorSlideMode.SMOOTH);
        binding.indicator.setIndicatorStyle(IndicatorStyle.ROUND_RECT);
        binding.indicator.setPageSize(Objects.requireNonNull(binding.viewPager2.getAdapter()).getItemCount());
        binding.indicator.notifyDataChanged();
    }

    private void navigateToCompanyFragment(Company company) {
        int currentDestinationId = currentDestination != null ? currentDestination.getId() : -1;

        if (currentDestinationId == R.id.homeFragment) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("company", company);
            navController.navigate(R.id.action_homeFragment_to_companyFragment, bundle);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(Company company) {
        navigateToCompanyFragment(company);
    }
}