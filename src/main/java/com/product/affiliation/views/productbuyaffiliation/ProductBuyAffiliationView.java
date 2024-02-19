package com.product.affiliation.views.productbuyaffiliation;

import com.product.affiliation.data.Monitor;
import com.product.affiliation.query.EqualsOperator;
import com.product.affiliation.query.InOperator;
import com.product.affiliation.query.Operator;
import com.product.affiliation.services.MonitorService;
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
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@PageTitle("Product Buy Affiliation")
@Route(value = "product-buy-affiliation")
@Uses(Icon.class)
public class ProductBuyAffiliationView extends Div {

    private Grid<Monitor> grid;
    private Filters filtersComponent;
    private final MonitorService monitorService;
    private final Map<String, Operator<String>> filtersCriteria;

    public ProductBuyAffiliationView(MonitorService monitorService) {
        this.monitorService = monitorService;
        this.filtersCriteria = new ConcurrentHashMap<>();

        setSizeFull();
        addClassNames("product-buy-affiliation-view");

        filtersComponent = new Filters(monitorService, () -> refreshGrid());
        VerticalLayout layout = new VerticalLayout(createMobileFilters(), filtersComponent, createGrid());
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().add(Lumo.DARK);

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
            if (filtersComponent.getClassNames().contains("visible")) {
                filtersComponent.removeClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:plus");
            } else {
                filtersComponent.addClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:minus");
            }
        });
        return mobileFilters;
    }

    public class Filters extends Div {
        private final MonitorService _monitorService;
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

        public Filters(MonitorService monitorService, Runnable onSearch) {
            _monitorService = monitorService;
            setWidthFull();
            addClassName("filter-layout");
            addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                    LumoUtility.BoxSizing.BORDER);
            brand.addClassName("double-width");
            condition.addClassName("double-width");
            connectivityTech.addClassName("double-width");
            priceFromField.setPrefixComponent(new Div("£"));
            priceToField.setPrefixComponent(new Div("£"));

            refreshRate.setItems(_monitorService.getProjectedUniqueItems("refreshRate"));
            brand.setItems(_monitorService.getProjectedUniqueItems("brand"));
            connectivityTech.setItems(_monitorService.getProjectedUniqueItems("connectivityTech"));
            condition.setItems(_monitorService.getProjectedUniqueItems("productCondition"));
            color.setItems(_monitorService.getProjectedUniqueItems("color"));
            screenSize.setItems(_monitorService.getProjectedUniqueItems("screenSize"));
            displayResolution.setItems(_monitorService.getProjectedUniqueItems("displayResolution"));
            displayType.setItems(_monitorService.getProjectedUniqueItems("displayType"));

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
                filtersCriteria.clear();
                onSearch.run();
            });

            Button searchBtn = new Button("Search");
            searchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            searchBtn.addClickListener(e -> {
                onSearch.run();
            });

            Div actions = new Div(resetBtn, searchBtn);
            actions.addClassName(LumoUtility.Gap.SMALL);
            actions.addClassName("actions");

            add(amazonChoiceYesOrNo, refreshRate, brand, connectivityTech, condition, priceFromField, priceToField, screenSize, displayResolution, displayType, color, actions);

            refreshRate.addValueChangeListener(valueChangeEvent ->
                    filtersCriteria.compute("refreshRate", (k, v) -> new EqualsOperator("refreshRate", Set.of(valueChangeEvent.getValue()))));

            brand.addSelectionListener(valueChangeEvent ->
                    filtersCriteria.compute("brand", (k, v) -> new InOperator("brand", valueChangeEvent.getAllSelectedItems())));

            connectivityTech.addSelectionListener(valueChangeEvent ->
                    filtersCriteria.compute("connectivityTech", (k, v) -> new InOperator("connectivityTech", valueChangeEvent.getAllSelectedItems())));

            screenSize.addSelectionListener(valueChangeEvent ->
                    filtersCriteria.compute("screenSize", (k, v) -> new InOperator("screenSize", valueChangeEvent.getAllSelectedItems())));

            displayResolution.addValueChangeListener(valueChangeEvent ->
                    filtersCriteria.compute("displayResolution", (k, v) -> new EqualsOperator("displayResolution", Set.of(valueChangeEvent.getValue()))));

            displayType.addValueChangeListener(valueChangeEvent ->
                    filtersCriteria.compute("displayType", (k, v) -> new EqualsOperator("displayType", Set.of(valueChangeEvent.getValue()))));

            condition.addSelectionListener(multiSelectionListener -> filtersCriteria.compute( "condition" , (k, v) -> new InOperator("screenSize", multiSelectionListener.getAllSelectedItems())));

            color.addSelectionListener(multiSelectionListener -> filtersCriteria.compute( "color" , (k, v) -> new InOperator("color", multiSelectionListener.getAllSelectedItems())));

            amazonChoiceYesOrNo.addValueChangeListener(valueChangeEvent ->
                    filtersCriteria.compute("amazonChoiceYesOrNo", (k, v) -> new EqualsOperator("amazonChoiceYesOrNo", Set.of(valueChangeEvent.getValue()))));
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

        grid.setItems(query -> monitorService.findAllMonitors(query.getOffset(), query.getLimit(), filtersCriteria).stream());

        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT, GridVariant.LUMO_COLUMN_BORDERS, GridVariant.LUMO_ROW_STRIPES);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        return grid;
    }

    private void refreshGrid() {
        Query<?, Map<String, Operator<String>>> queryFilter = new Query<>(filtersCriteria);
        List<Monitor> allMonitors = monitorService.findAllMonitors(queryFilter.getOffset(), queryFilter.getLimit(),
                queryFilter.getFilter().orElse(Collections.emptyMap()));

        grid.setItems(allMonitors);
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
