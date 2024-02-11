package com.product.affiliation.views.productbuyaffiliation;

import com.product.affiliation.data.Monitor;
import com.product.affiliation.services.SamplePersonService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

@PageTitle("Product Buy Affiliation")
@Route(value = "product-buy-affiliation")
@Uses(Icon.class)
public class ProductBuyAffiliationView extends Div {

    private Grid<Monitor> grid;

    private Filters filters;
    private final SamplePersonService samplePersonService;

    public ProductBuyAffiliationView(SamplePersonService SamplePersonService) {
        this.samplePersonService = SamplePersonService;
        setSizeFull();
        addClassNames("product-buy-affiliation-view");

        filters = new Filters(() -> refreshGrid());
        VerticalLayout layout = new VerticalLayout(createMobileFilters(), filters, createGrid());
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        add(layout);
    }

    private HorizontalLayout createMobileFilters() {
        // Mobile version
        HorizontalLayout mobileFilters = new HorizontalLayout();
        mobileFilters.setWidthFull();
        mobileFilters.addClassNames(LumoUtility.Padding.MEDIUM, LumoUtility.BoxSizing.BORDER,
                LumoUtility.AlignItems.CENTER);
        mobileFilters.addClassName("mobile-filters");

        Icon mobileIcon = new Icon("lumo", "plus");
        Span filtersHeading = new Span("Filters");
        mobileFilters.add(mobileIcon, filtersHeading);
        mobileFilters.setFlexGrow(1, filtersHeading);
        mobileFilters.addClickListener(e -> {
            if (filters.getClassNames().contains("visible")) {
                filters.removeClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:plus");
            } else {
                filters.addClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:minus");
            }
        });
        return mobileFilters;
    }

    public static class Filters extends Div {
        private final Checkbox amazonChoiceYesOrNo = new Checkbox("Is Amazon choice?");
        private final ComboBox<String> refreshRate = new ComboBox("Refresh Rate");
        private final MultiSelectComboBox<String> brand = new MultiSelectComboBox<>("Brand");
        private final MultiSelectComboBox<String> connectivityTech = new MultiSelectComboBox<>("Connectivity Tech");
        private final CheckboxGroup<String> condition = new CheckboxGroup<>("Condition");
        private final NumberField priceFromField = new NumberField("Price From");
        private final NumberField priceToField = new NumberField("Price To");
        private final MultiSelectComboBox<String> screenSize = new MultiSelectComboBox<>("Screen Size");
        private final ComboBox<String> displayResolution = new ComboBox<>("Max display resolution");
        private final ComboBox<String> displayType = new ComboBox<>("Display Type");
        private final CheckboxGroup<String> color = new CheckboxGroup<>("Colour");

        public Filters(Runnable onSearch) {
            setWidthFull();
            addClassName("filter-layout");
            addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                    LumoUtility.BoxSizing.BORDER);

            refreshRate.setItems("60 Hz, 100 Hz"); //TODO: COme from database/backend
            brand.setItems("Dell", "HP"); //TODO: COme from backend
            brand.addClassName("double-width");
            connectivityTech.setItems("VGA", "HDMI"); //TODO: COme from backend
            connectivityTech.addClassName("double-width");
            condition.setItems("New", "Used"); //TODO: COme from backend
            condition.addClassName("double-width");
            priceFromField.setPrefixComponent(new Div("£"));
            priceToField.setPrefixComponent(new Div("£"));
            screenSize.setItems("27 Inch, 32 Inch"); //TODO: COme from backend
            displayResolution.setItems("1920 x 3242", "2560 x 1990"); //TODO: COme from backend
            displayType.setItems("Ips"); //TODO: COme from backend
            color.setItems("Black", "White");

            // Action buttons
            Button resetBtn = new Button("Reset");
            resetBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            resetBtn.addClickListener(e -> {
                amazonChoiceYesOrNo.clear();
                refreshRate.clear();
                brand.clear();
                connectivityTech.clear();
                condition.clear();
                priceFromField.clear();
                priceToField.clear();
                screenSize.clear();
                displayResolution.clear();
                displayType.clear();
                color.clear();

                onSearch.run();
            });

            Button searchBtn = new Button("Search");
            searchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            searchBtn.addClickListener(e -> onSearch.run());

            Div actions = new Div(resetBtn, searchBtn);
            actions.addClassName(LumoUtility.Gap.SMALL);
            actions.addClassName("actions");

            add(amazonChoiceYesOrNo, refreshRate, brand, connectivityTech, condition, priceFromField, priceToField, screenSize, displayResolution, displayType, color, actions);
        }
    }

    private Component createGrid() {
        grid = new Grid<>(Monitor.class, false);

        grid.addColumn("id").setVisible(false);
        grid.addColumn("amazonChoice").setVisible(false);
        grid.addColumn("name").setHeader("Product Name").setAutoWidth(true);
        grid.addColumn(createAffiliateURLRenderer()).setHeader("Affiliate URL").setAutoWidth(true);
        grid.addColumn(new TextRenderer<>(m -> m.getProductCondition().name())).setHeader("Condition").setAutoWidth(true);
        grid.addColumn(new NumberRenderer<>(Monitor::getPrice, NumberFormat.getCurrencyInstance())).setHeader("Price").setAutoWidth(true);
        grid.addColumn(new TextRenderer<>(m -> String.format("%d %s", m.getWarrantyValue(), m.getUnitOfWarranty()))).setHeader("Warranty").setAutoWidth(false);
        grid.addColumn(new TextRenderer<>(m -> String.format("%d %s", m.getScreenSize(), m.getSizeUnit()))).setHeader("Screen Size").setAutoWidth(true);
        grid.addColumn(new TextRenderer<>(m -> String.format("%d %s", m.getRefreshRate(), m.getRefreshRateUnit()))).setHeader("Refresh Rate").setAutoWidth(true);
        grid.addColumn("maxDisplayResolution").setHeader("Max Display Resolution").setAutoWidth(true);
        grid.addColumn(new TextRenderer<>(m -> String.format("%s", m.getDisplayType().name()))).setHeader("Display Type").setAutoWidth(true);
        grid.addColumn("dimension").setHeader("Dimension").setAutoWidth(true);
        grid.addColumn(createSpecialFeaturesRenderer()).setHeader("Special Features").setAutoWidth(true);
        grid.addColumn("brand").setHeader("Brand").setAutoWidth(true);
        grid.addColumn("color").setHeader("Color").setAutoWidth(true);

        /*rid.setItems(query -> samplePersonService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
                filters).stream());*/
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        return grid;
    }

    private void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }

    private ComponentRenderer<Anchor, Monitor> createAffiliateURLRenderer() {
        return new ComponentRenderer<>(monitor -> new Anchor(monitor.getAffiliateURL().toString(), monitor.getName()));
    }

    private TextRenderer<Monitor> createSpecialFeaturesRenderer() {
        ItemLabelGenerator<Monitor> labelGenFunc = m -> m.getSpecialFeatures().stream().map(
                Monitor.SpecialFeature::toString).collect(Collectors.joining(", "));

        return new TextRenderer<>(labelGenFunc);
    }
}
