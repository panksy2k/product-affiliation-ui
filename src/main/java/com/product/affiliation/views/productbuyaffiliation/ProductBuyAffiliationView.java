package com.product.affiliation.views.productbuyaffiliation;

import com.product.affiliation.data.Product;
import com.product.affiliation.query.EqualsOperator;
import com.product.affiliation.query.GtOperator;
import com.product.affiliation.query.InOperator;
import com.product.affiliation.query.LtOperator;
import com.product.affiliation.services.MonitorService;
import com.product.affiliation.util.CompletableFutures;
import com.product.affiliation.util.Threads;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@PageTitle("Product Buy Affiliation")
@Route(value = "home")
/*@RouteAlias(value = "home")
@RouteAlias(value = "main")
@RouteAlias(value = "monitor/purchase")
*/
@Uses(Icon.class)
public class ProductBuyAffiliationView extends Div {

    private Grid<Product> grid;
    private Filters filtersComponent;
    private final MonitorService monitorService;
    private FilterCriteria filterCriteria;

    public ProductBuyAffiliationView(MonitorService monitorService) {
        this.monitorService = monitorService;
        this.filterCriteria = new FilterCriteria();

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

            fetchFilterData(Threads.newFixedThreadPool("Filters", "drop-downs", 2));

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

                filterCriteria.clear();
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

            add(brand, connectivityTech, refreshRate, screenSize, displayResolution, displayType, priceFromField, priceToField, amazonChoiceYesOrNo, condition, color, actions);

            brand.addSelectionListener(valueChangeEvent -> filterCriteria.addDataOperation("brand",
                    (k, v) -> !Objects.isNull(valueChangeEvent.getValue()) && !valueChangeEvent.getValue().isEmpty()?
                                    new InOperator<String>(Set.<String>copyOf(valueChangeEvent.getValue())) :
                            v));
            connectivityTech.addSelectionListener(valueChangeEvent -> filterCriteria.addDataOperation("connectivityTech",
                    (k, v) -> !Objects.isNull(valueChangeEvent.getValue()) && !valueChangeEvent.getValue().isEmpty()?
                                    new InOperator<String>(Set.copyOf(valueChangeEvent.getValue())) :
                            v));
            screenSize.addSelectionListener(valueChangeEvent -> filterCriteria.addDataOperation("screenSize",
                    (k, v) -> !Objects.isNull(valueChangeEvent.getValue()) && !valueChangeEvent.getValue().isEmpty()?
                                    new InOperator<String>(Set.copyOf(valueChangeEvent.getValue())) :
                            v));
            condition.addSelectionListener(valueChangeEvent -> filterCriteria.addDataOperation("productCondition",
                    (k, v) -> !Objects.isNull(valueChangeEvent.getValue()) && !valueChangeEvent.getValue().isEmpty()?
                                    new InOperator<String>(Set.copyOf(valueChangeEvent.getValue())) :
                            v));
            color.addSelectionListener(valueChangeEvent -> filterCriteria.addDataOperation("color",
                    (k, v) -> !Objects.isNull(valueChangeEvent.getValue()) && !valueChangeEvent.getValue().isEmpty()?
                                    new InOperator<String>(Set.copyOf(valueChangeEvent.getValue())) :
                            v));

            refreshRate.addValueChangeListener(valueChangeEvent -> filterCriteria.addDataOperation("refreshRate",
                    (k, v) -> !Objects.isNull(valueChangeEvent.getValue())?
                                    new EqualsOperator(Set.of(valueChangeEvent.getValue())) :
                            v));
            displayResolution.addValueChangeListener(valueChangeEvent -> filterCriteria.addDataOperation("displayResolution",
                    (k, v) -> !Objects.isNull(valueChangeEvent.getValue())?
                                    new EqualsOperator(Set.of(valueChangeEvent.getValue())) :
                            v));
            displayType.addValueChangeListener(valueChangeEvent -> filterCriteria.addDataOperation("displayType",
                    (k, v) -> !Objects.isNull(valueChangeEvent.getValue())?
                                    new EqualsOperator(Set.of(valueChangeEvent.getValue())) :
                            v));
            amazonChoiceYesOrNo.addValueChangeListener(valueChangeEvent -> filterCriteria.addDataOperation("amazonChoiceYesOrNo",
                    (k, v) -> !Objects.isNull(valueChangeEvent.getValue())?
                                    new EqualsOperator(Set.of(valueChangeEvent.getValue())) :
                            v));

            priceFromField.addValueChangeListener(valueChangeEvent -> filterCriteria.addDataOperation("priceFrom",
                    (k, v) -> !Objects.isNull(valueChangeEvent.getValue())?
                                    new GtOperator(Set.of(valueChangeEvent.getValue())) :
                            v));
            priceToField.addValueChangeListener(valueChangeEvent -> filterCriteria.addDataOperation("priceTo",
                    (k, v) -> !Objects.isNull(valueChangeEvent.getValue())?
                                    new LtOperator(Set.of(valueChangeEvent.getValue())) :
                            v));
        }

        private void fetchFilterData(ExecutorService filterExecutors) {
            CompletableFuture<List<String>> refreshRateFuture = _monitorService.getProjectedUniqueItems("refreshRate", filterExecutors);
            refreshRateFuture.thenAccept(refreshRate::setItems);

            CompletableFuture<List<String>> refreshBrand = _monitorService.getProjectedUniqueItems("brand", filterExecutors);
            refreshBrand.thenAccept(brand::setItems);

            CompletableFuture<List<String>> refreshConnectivityTech = _monitorService.getProjectedUniqueItems("connectivityTech", filterExecutors);
            refreshConnectivityTech.thenAccept(connectivityTech::setItems);

            CompletableFuture<List<String>> refreshProductCondition = _monitorService.getProjectedUniqueItems("productCondition", filterExecutors);
            refreshProductCondition.thenAccept(condition::setItems);

            CompletableFuture<List<String>> refreshColor = _monitorService.getProjectedUniqueItems("color", filterExecutors);
            refreshColor.thenAccept(color::setItems);

            CompletableFuture<List<String>> refreshScreenSize = _monitorService.getProjectedUniqueItems("screenSize", filterExecutors);
            refreshScreenSize.thenAccept(screenSize::setItems);

            CompletableFuture<List<String>> refreshDisplayResolution = _monitorService.getProjectedUniqueItems("displayResolution", filterExecutors);
            refreshDisplayResolution.thenAccept(displayResolution::setItems);

            CompletableFuture<List<String>> refreshDisplayType = _monitorService.getProjectedUniqueItems("displayType", filterExecutors);
            refreshDisplayType.thenAccept(displayType::setItems);

            List<CompletableFuture<List<String>>> allFutureList = Arrays.asList(refreshRateFuture, refreshBrand,
                    refreshConnectivityTech, refreshColor, refreshProductCondition, refreshScreenSize,
                    refreshDisplayResolution, refreshDisplayType);

            try {
                for(CompletableFuture<List<String>> future : allFutureList) {
                    CompletableFutures.uncheckedAwait(future, 30, TimeUnit.SECONDS);
                }
            } catch (InterruptedException e) {

            }

            filterExecutors.shutdown();
        }
    }

    private Component createGrid() {
        grid = new Grid<>(Product.class, false);

        grid.addColumn("id").setVisible(false);
        grid.addColumn("amazonChoice").setVisible(false);
        grid.addColumn("name").setHeader("Product Name").setAutoWidth(true);
        grid.addColumn(createAffiliateURLRenderer()).setHeader("Affiliate URL").setAutoWidth(true);
        grid.addColumn(new TextRenderer<>(m -> m.getProductCondition().name())).setHeader("Condition").setAutoWidth(true);
        grid.addColumn(new NumberRenderer<>(Product::getPrice, NumberFormat.getCurrencyInstance())).setHeader("Price").setAutoWidth(true);
        grid.addColumn(new TextRenderer<>(m -> String.format("%s", m.getWarrantyValue()))).setHeader("Warranty").setAutoWidth(false);
        grid.addColumn(new TextRenderer<>(m -> String.format("%s", m.getScreenSize()))).setHeader("Screen Size").setAutoWidth(true);
        grid.addColumn(new TextRenderer<>(m -> String.format("%s", m.getRefreshRate()))).setHeader("Refresh Rate").setAutoWidth(true);
        grid.addColumn("maxDisplayResolution").setHeader("Max Display Resolution").setAutoWidth(true);
        grid.addColumn(new TextRenderer<>(m -> String.format("%s", m.getDisplayType().name()))).setHeader("Display Type").setAutoWidth(true);
        grid.addColumn("dimension").setHeader("Dimension").setAutoWidth(true);
        grid.addColumn(createSpecialFeaturesRenderer()).setHeader("Special Features").setAutoWidth(true);
        grid.addColumn("brand").setHeader("Brand").setAutoWidth(true);
        grid.addColumn("color").setHeader("Color").setAutoWidth(true);

        grid.setItems(query -> monitorService.findAllMonitors(query.getOffset(), query.getLimit(), filterCriteria).stream());

        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT, GridVariant.LUMO_COLUMN_BORDERS, GridVariant.LUMO_ROW_STRIPES);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        return grid;
    }

    private void refreshGrid() {
        Query<?, FilterCriteria> queryFilter = new Query<>(filterCriteria);
        List<Product> allProducts = monitorService.findAllMonitors(queryFilter.getOffset(), queryFilter.getLimit(),
                queryFilter.getFilter().orElse(null));

        grid.setItems(allProducts);
        grid.getDataProvider().refreshAll();
    }

    private ComponentRenderer<Anchor, Product> createAffiliateURLRenderer() {
        return new ComponentRenderer<>(product -> new Anchor(product.getAffiliateURL().toString(), product.getName()));
    }

    private TextRenderer<Product> createSpecialFeaturesRenderer() {
        ItemLabelGenerator<Product> labelGenFunc = m -> m.getSpecialFeatures().stream().map(
                Product.SpecialFeature::toString).collect(Collectors.joining(", "));

        return new TextRenderer<>(labelGenFunc);
    }
}
