package ru.tds.realestateagency.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.tds.realestateagency.DatabaseHandler;
import ru.tds.realestateagency.Helper;
import ru.tds.realestateagency.entities.Deal;
import ru.tds.realestateagency.entities.Demand;
import ru.tds.realestateagency.entities.Offer;
import ru.tds.realestateagency.entities.Realtor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Класс-контроллер для обработки событий на экране "Сделки"
 *
 * @author Трушенков Дмитрий
 */
public class DealsController {

    private static final String PATH_TO_HOME_SCREEN = "/ru/tds/realestateagency/views/main.fxml";
    private static final String HOME = "Дом";
    private static final String FLAT = "Квартира";
    private static final String LAND = "Земля";
    private static final String REALTORS_TABLE = "realtors";

    //Константы для работы с таблицей Потребностей
    private static final String DEMAND_TABLE = "demands";
    private static final String DEMAND_ID_COLUMN = "id";
    private static final String DEMAND_CLIENT_COLUMN = "client";
    private static final String DEMAND_REALTOR_COLUMN = "realtor";
    private static final String DEMAND_TYPE_REAL_ESTATE_COLUMN = "typeRealEstate";
    private static final String DEMAND_ADDRESS_COLUMN = "address";
    private static final String DEMAND_MIN_PRICE_COLUMN = "minPrice";
    private static final String DEMAND_MAX_PRICE_COLUMN = "maxPrice";
    private static final String DEMAND_MIN_SQUARE_COLUMN = "minSquare";
    private static final String DEMAND_MAX_SQUARE_COLUMN = "maxSquare";
    private static final String DEMAND_MIN_COUNT_OF_ROOMS_COLUMN = "minCountOfRooms";
    private static final String DEMAND_MAX_COUNT_OF_ROOMS_COLUMN = "maxCountOfRooms";
    private static final String DEMAND_MIN_NUMBER_OF_FLOORS_COLUMN = "minNumberOfFloors";
    private static final String DEMAND_MAX_NUMBER_OF_FLOORS_COLUMN = "maxNumberOfFloors";
    private static final String DEMAND_MIN_FLOOR = "minFloor";
    private static final String DEMAND_MAX_FLOOR = "maxFloor";

    //Константы для работы с таблицей Предложений
    private static final String OFFER_TABLE = "offers";
    private static final String OFFER_ID_COLUMN = "id";
    private static final String OFFER_CLIENT_COLUMN = "client";
    private static final String OFFER_REALTOR_COLUMN = "realtor";
    private static final String OFFER_REAL_ESTATE_COLUMN = "realEstate";
    private static final String OFFER_PRICE_COLUMN = "price";

    //Константы для работы с таблицей Сделок
    private static final String DEALS_TABLE = "deals";
    private static final String DEALS_ID_COLUMN = "id";
    private static final String DEALS_DEMAND_COLUMN = "demand";
    private static final String DEALS_OFFER_COLUMN = "offer";

    @FXML
    public Label costOfServiceForClientSellerLabel;
    @FXML
    public Label costOfServiceForClientBuyerLabel;
    @FXML
    public Label amountOfDeductionRealtorClientSellerLabel;
    @FXML
    public Label amountOfDeductionRealtorClientBuyerLabel;
    @FXML
    public Label amountOfDeductionCompanyLabel;
    @FXML
    public Label costOfServiceForClientSeller;
    @FXML
    public Label costOfServiceForClientBuyer;
    @FXML
    public Label amountOfDeductionRealtorClientSeller;
    @FXML
    public Label amountOfDeductionRealtorClientBuyer;
    @FXML
    public Label amountOfDeductionCompany;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TableView<Deal> tableDeals;
    @FXML
    private TableColumn<Deal, String> demandTableColumn;
    @FXML
    private TableColumn<Deal, String> offerTableColumn;
    @FXML
    private ComboBox<Demand> demandComboBox;
    @FXML
    private ComboBox<Offer> offerComboBox;
    @FXML
    private Button createDealButton;
    @FXML
    private Button updateDealButton;
    @FXML
    private Button deleteDealButton;
    //Списки для храненеия информации из базы данных
    private ObservableList<Demand> listOfDemands;//список потребностей
    private ObservableList<Offer> listOfOffers;//список предложений
    private ObservableList<Realtor> listOfRealtors;//список риэлторов
    //    private ObservableList<Client> listOfClients;//список клиентов
    private ArrayList<Integer> idDemandArray;//список id потрбеностей
    private ArrayList<Integer> idOfferArray;//список id предложений
    private ArrayList<Integer> idDealsArray;//список id сделок
//    private ArrayList<Integer> idRealtorsArray;//список id риэлторов
//    private ArrayList<Integer> idClientsArray;//список id риэлторов

    private int idSelectedDeal;//ID выбранной сделки
//    private int idSelectedDemand;//ID выбранной потребности
//    private int idSelectedOffer;//ID выбранного предложения

    @FXML
    void goHomeScreen(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
        //переход на главный экран
        Helper.openNewScreen(PATH_TO_HOME_SCREEN);
    }

    @FXML
    void initialize() {

        //Видимость Label = false
        costOfServiceForClientSellerLabel.setVisible(false);
        costOfServiceForClientBuyerLabel.setVisible(false);
        amountOfDeductionRealtorClientSellerLabel.setVisible(false);
        amountOfDeductionRealtorClientBuyerLabel.setVisible(false);
        amountOfDeductionCompanyLabel.setVisible(false);

        costOfServiceForClientSeller.setVisible(false);
        costOfServiceForClientBuyer.setVisible(false);
        amountOfDeductionRealtorClientSeller.setVisible(false);
        amountOfDeductionRealtorClientBuyer.setVisible(false);
        amountOfDeductionCompany.setVisible(false);

        updateDealButton.setDisable(true);
        deleteDealButton.setDisable(true);


        //Обнуление выпадающих списков и отмена выбора строки в таблице при нажатии на область окна приложения
        mainPane.setOnMousePressed(event -> {
            idSelectedDeal = 0;
            //отмена выбора строки в таблице
            tableDeals.getSelectionModel().clearSelection();
            //обнуление выпадающих списков
            demandComboBox.setValue(null);
            offerComboBox.setValue(null);

            //Видимость Label = false
            costOfServiceForClientSellerLabel.setVisible(false);
            costOfServiceForClientBuyerLabel.setVisible(false);
            amountOfDeductionRealtorClientSellerLabel.setVisible(false);
            amountOfDeductionRealtorClientBuyerLabel.setVisible(false);
            amountOfDeductionCompanyLabel.setVisible(false);

            costOfServiceForClientSeller.setVisible(false);
            costOfServiceForClientBuyer.setVisible(false);
            amountOfDeductionRealtorClientSeller.setVisible(false);
            amountOfDeductionRealtorClientBuyer.setVisible(false);
            amountOfDeductionCompany.setVisible(false);

        });

        //инициализация коллекции объектов потребостей и предложений
        listOfDemands = createListOfDemands(getDataFromDB(DEMAND_TABLE));
        listOfOffers = createListOfOffers(getDataFromDB(OFFER_TABLE));
        listOfRealtors = createListOfRealtors(getDataFromDB(REALTORS_TABLE));

        //установка значений в выпадающие списки
        demandComboBox.setItems(listOfDemands);
        offerComboBox.setItems(listOfOffers);

        //определение полей таблицы с соотвествующими полями объекта
        demandTableColumn.setCellValueFactory(new PropertyValueFactory<>(DEALS_DEMAND_COLUMN));
        offerTableColumn.setCellValueFactory(new PropertyValueFactory<>(DEALS_OFFER_COLUMN));

        demandTableColumn.setEditable(false);
        offerTableColumn.setEditable(false);

        tableDeals.setItems(createListOfDeals(getDataFromDB(DEALS_TABLE)));

        System.out.println("ArrayList DEALS ID: " + idDealsArray);
        System.out.println("ArrayList DEMANDS ID: " + idDemandArray);
        System.out.println("ArrayList OFFER ID: " + idOfferArray);

        //слушатель для обработки событий при объекта в таблице
        tableDeals.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                idSelectedDeal = idDealsArray.get(tableDeals.getSelectionModel().getSelectedIndex());
                System.out.println("ID сделки из базы  = " + idSelectedDeal);

                //Слушатели для текстовых полей для проверки на изменение какого либо значения и предоставления возможности обновления клиента
                demandComboBox.valueProperty().addListener((observable1, oldValue1, demand) -> {
                    try {
                        if (!tableDeals.getSelectionModel().getSelectedItem().getDemand().equals(demand.toString())) {
                            updateDealButton.setDisable(false);
                        } else if (tableDeals.getSelectionModel().getSelectedItem().getDemand().equals(demand.toString())) {
                            updateDealButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                offerComboBox.valueProperty().addListener((observable1, oldValue1, offer) -> {
                    try {
                        if (!tableDeals.getSelectionModel().getSelectedItem().getOffer().equals(offer.toString())) {
                            updateDealButton.setDisable(false);
                        } else if (tableDeals.getSelectionModel().getSelectedItem().getOffer().equals(offer.toString())) {
                            updateDealButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                //установка значения клиента выбранного объекта в соответствующий выпадающий список
                if (tableDeals.getSelectionModel().getSelectedItem().getDemand() != null) {
                    //получаем айди клиента из списка всех клиентов
                    for (int i = 0; i < listOfDemands.size(); i++) {
                        if (tableDeals.getSelectionModel().getSelectedItem().getDemand().equals(listOfDemands.get(i).toString())) {
                            //устанавливаем в выпадающий список клиента
                            demandComboBox.getSelectionModel().select(i);
                        }
                    }
                }

                //установка значения риэлтора выбранного объекта в соответствующий выпадающий список
                if (tableDeals.getSelectionModel().getSelectedItem().getOffer() != null) {
                    //получаем айди клиента из списка всех риэлторов
                    for (int i = 0; i < listOfOffers.size(); i++) {
                        if (tableDeals.getSelectionModel().getSelectedItem().getOffer().equals(listOfOffers.get(i).toString())) {
                            //устанавливаем в выпадающий список риэлторов
                            offerComboBox.getSelectionModel().select(i);
                        }
                    }
                }

                createDealButton.setDisable(true);
                updateDealButton.setDisable(true);
                deleteDealButton.setDisable(false);


                double sumClientSeller = 0;
                double sumClientBuyer = 0;
                double sumRealtorSeller = 0;
                double sumRealtorBuyer = 0;
                double sumCompany = 0;

                Offer selectedOffer = null;
                Demand selectedDemand = null;
                Realtor realtorClientSeller = null;
                Realtor realtorClientBuyer = null;

                //Если коллекция предложений содержит выбранный в таблице элемент, то создаем объект типа Offer
                for (Offer offerInstance : listOfOffers) {
                    if (offerInstance.toString().equals(tableDeals.getSelectionModel().getSelectedItem().getOffer())) {
                        selectedOffer = offerInstance;
                    }
                }

                //Если коллекция предложений содержит выбранный в таблице элемент, то создаем объект типа Offer
                for (Demand demandInstance : listOfDemands) {
                    if (demandInstance.toString().equals(tableDeals.getSelectionModel().getSelectedItem().getDemand())) {
                        selectedDemand = demandInstance;
                    }
                }

                //проверка на то, что предложение не равно null и потребность не равна null
                if (selectedOffer != null && selectedDemand != null) {

                    for (Realtor realtorInstance : listOfRealtors) {
                        if (realtorInstance.getLastName().equals(selectedOffer.getRealtor())) {
                            realtorClientSeller = realtorInstance;
                        }
                        if (realtorInstance.getLastName().equals(selectedDemand.getRealtor())) {
                            realtorClientBuyer = realtorInstance;
                        }
                    }

                    System.out.println("Риэлтор продавца: " + realtorClientSeller);
                    System.out.println("Риэлтор покупателя: " + realtorClientBuyer);

                    //проверка на то, что риэлтор клиента-продавца не равно null
                    if (realtorClientSeller != null) {

                        //рассчитываем сумму услуг для клиента продавца
                        if (selectedOffer.getRealEstate().contains(HOME)) {
                            sumClientSeller = 30000 + (0.01 * selectedOffer.getPrice());
                        } else if (selectedOffer.getRealEstate().contains(FLAT)) {
                            sumClientSeller = 36000 + (0.01 * selectedOffer.getPrice());
                        } else if (selectedOffer.getRealEstate().contains(LAND)) {
                            sumClientSeller = 30000 + (0.02 * selectedOffer.getPrice());
                        }

                        //рассчитываем размер отчислений для риэлтора клиента-продавца
                        sumRealtorSeller = sumClientSeller * realtorClientSeller.getCommissionPart() / 100;

                    }

                    //проверка на то, что риэлтор клиента-покупателя не равен null
                    if (realtorClientBuyer != null) {

                        //рассчитываем сумму услуг для клиента покупателя
                        sumClientBuyer = selectedOffer.getPrice() * 0.03;

                        //рассчитываем размер отчислений для риэлтора клиента-покупателя
                        sumRealtorBuyer = sumClientBuyer * realtorClientBuyer.getCommissionPart() / 100;

                    }

                    //расситываем размер отчислений компании
                    sumCompany = (sumClientSeller - sumRealtorSeller) + (sumClientBuyer - sumRealtorBuyer);

                }


                //проверка на то, что


                try {
                    costOfServiceForClientSeller.setText(String.format("%.2f", sumClientSeller) + " " + URLDecoder.decode("%E2%82%BD", "UTF-8"));
                    costOfServiceForClientBuyer.setText(String.format("%.2f", sumClientBuyer) + " " + URLDecoder.decode("%E2%82%BD", "UTF-8"));
                    amountOfDeductionRealtorClientSeller.setText(String.format("%.2f", sumRealtorSeller) + " " + URLDecoder.decode("%E2%82%BD", "UTF-8"));
                    amountOfDeductionRealtorClientBuyer.setText(String.format("%.2f", sumRealtorBuyer) + " " + URLDecoder.decode("%E2%82%BD", "UTF-8"));
                    amountOfDeductionCompany.setText(String.format("%.2f", sumCompany) + " " + URLDecoder.decode("%E2%82%BD", "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                //Видимость Label = true
                costOfServiceForClientSellerLabel.setVisible(true);
                costOfServiceForClientBuyerLabel.setVisible(true);
                amountOfDeductionRealtorClientSellerLabel.setVisible(true);
                amountOfDeductionRealtorClientBuyerLabel.setVisible(true);
                amountOfDeductionCompanyLabel.setVisible(true);

                costOfServiceForClientSeller.setVisible(true);
                costOfServiceForClientBuyer.setVisible(true);
                amountOfDeductionRealtorClientSeller.setVisible(true);
                amountOfDeductionRealtorClientBuyer.setVisible(true);
                amountOfDeductionCompany.setVisible(true);

            } else {
                createDealButton.setDisable(false);
                updateDealButton.setDisable(true);
                deleteDealButton.setDisable(true);
            }
        });
    }


    @FXML
    void createDeal() {
        //проверка на то что все поля заполнены
        if (demandComboBox.getValue() != null && offerComboBox.getValue() != null) {

            //SQL запрос для добавления новой сделки в базу данных
            String insertDeal = String.format("INSERT INTO %s(%s, %s) VALUES (?,?);",
                    DEALS_TABLE,
                    DEALS_DEMAND_COLUMN,
                    DEALS_OFFER_COLUMN);

            try {
                PreparedStatement addDealStatement = new DatabaseHandler().createDbConnection().prepareStatement(insertDeal);
                addDealStatement.setString(1, demandComboBox.getValue().toString());
                addDealStatement.setString(2, offerComboBox.getValue().toString());

                //выполнение запроса
                addDealStatement.executeUpdate();

                //открываем диалоговое окно для уведомления об успешном добавлении
                showAlert(
                        "Операция успешно выполнена",
                        "Новая сделка успешно добавлена!",
                        Alert.AlertType.INFORMATION);

                //обнуляем выпадающие списки после добавления
                demandComboBox.setValue(null);
                offerComboBox.setValue(null);

                tableDeals.setItems(createListOfDeals(getDataFromDB(DEALS_TABLE)));

            } catch (SQLException e) {
                //открываем диалоговое окно для уведомления об ошибке
                showAlert(
                        "Ошибка добавления нового предложения",
                        "Предложение не было добавлено в базу. Повторите еще раз",
                        Alert.AlertType.ERROR);
            }


        } else {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка добавления новой сделки",
                    "Все поля являются обязательными к заполнению.",
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    void updateDeal() {
        //SQL запрос для обновления предложения
        String updateDeal = String.format("UPDATE %s SET %s=?,%s=? WHERE %s=?",
                DEALS_TABLE,
                DEALS_DEMAND_COLUMN,
                DEALS_OFFER_COLUMN,
                DEALS_ID_COLUMN);

        if (demandComboBox.getValue() != null && offerComboBox.getValue() != null) {
            try {
                PreparedStatement updateDealStatement = new DatabaseHandler().createDbConnection().prepareStatement(updateDeal);

                //установка значений для вставки в запрос
                updateDealStatement.setString(1, demandComboBox.getValue().toString());
                updateDealStatement.setString(2, offerComboBox.getValue().toString());
                updateDealStatement.setInt(3, idSelectedDeal);

                //выполнение SQL запроса
                updateDealStatement.executeUpdate();

                //заполняем таблицу данным из БД
                tableDeals.setItems(createListOfDeals(getDataFromDB(DEALS_TABLE)));

                //открываем диалоговое окно для уведомления об успешном обновлении
                showAlert(
                        "Операция успешно выполнена",
                        "Обновление сделки выполнено успешно!",
                        Alert.AlertType.INFORMATION);

                //Обнуляем выпадаюище списки после обновления
                demandComboBox.setValue(null);
                offerComboBox.setValue(null);

            } catch (SQLException e) {
                //открываем диалоговое окно для уведомления об ошибке
                showAlert(
                        "Ошибка обновления сделки",
                        "Обновление сделки не выполнено! Повторите еще раз",
                        Alert.AlertType.ERROR);
            }
        } else {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка обновления сделки",
                    "Все поля являются обязательными для заполнения.",
                    Alert.AlertType.ERROR
            );
        }
    }

    @FXML
    void deleteDeal() {
        //SQL запрос на удаление предложения
        String deleteDeal = String.format("DELETE FROM %s WHERE %s=?", DEALS_TABLE, DEALS_ID_COLUMN);

        try {
            PreparedStatement deleteDealStatement = new DatabaseHandler().createDbConnection().prepareStatement(deleteDeal);
            //установка значений для вставки в запрос
            deleteDealStatement.setInt(1, idSelectedDeal);
            //выполнение запроса на удаление
            deleteDealStatement.executeUpdate();

            //открываем диалоговое окно для уведомления об успешном удалении
            showAlert(
                    "Операция успешно выполнена",
                    "Удаление сделки выполнено успешно!",
                    Alert.AlertType.INFORMATION);

            //Обнуляем выпадаюище списки после удаления
            demandComboBox.setValue(null);
            offerComboBox.setValue(null);

            tableDeals.setItems(createListOfDeals(getDataFromDB(DEALS_TABLE)));

        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка удаления сделки",
                    String.format("Возникла ошибка при удалении сделки с ID = %d", idSelectedDeal),
                    Alert.AlertType.ERROR
            );
        }
    }

    private ObservableList<Deal> createListOfDeals(ResultSet resultSet) {
        ObservableList<Deal> list = FXCollections.observableArrayList();
        idDealsArray = new ArrayList<>();
        try {
            while (resultSet.next()) {
                //Создание объекта "Потребность"
                Deal deal = new Deal();
                deal.setDemand(resultSet.getString(DEALS_DEMAND_COLUMN));
                deal.setOffer(resultSet.getString(DEALS_OFFER_COLUMN));

                System.out.println(deal);

                //добавляем потребность в список
                list.add(deal);
                //добавляем ID  потребности в список
                idDealsArray.add(resultSet.getInt(DEMAND_ID_COLUMN));
            }
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка формирования коллекции потребностей",
                    "Возникла ошибка при формировании списка потребностей в доме из набора данных, полученных из базы",
                    Alert.AlertType.ERROR);
        }

        return list;
    }

    /**
     * Метод для формирования ObservableList с потребностиями в доме из набора данных
     *
     * @param resultSet набор данных из таблицы `demands`
     * @return ObservableList с объектами класса Demand
     */
    private ObservableList<Demand> createListOfDemands(ResultSet resultSet) {
        ObservableList<Demand> list = FXCollections.observableArrayList();
        idDemandArray = new ArrayList<>();
        try {
            while (resultSet.next()) {
                //Создание объекта "Потребность"
                Demand demand = new Demand();
                demand.setClient(resultSet.getString(DEMAND_CLIENT_COLUMN));
                demand.setRealtor(resultSet.getString(DEMAND_REALTOR_COLUMN));
                demand.setTypeRealEstate(resultSet.getString(DEMAND_TYPE_REAL_ESTATE_COLUMN));
                demand.setAddress(resultSet.getString(DEMAND_ADDRESS_COLUMN));
                demand.setMinPrice(resultSet.getInt(DEMAND_MIN_PRICE_COLUMN));
                demand.setMaxPrice(resultSet.getInt(DEMAND_MAX_PRICE_COLUMN));
                demand.setMinSquare(resultSet.getInt(DEMAND_MIN_SQUARE_COLUMN));
                demand.setMaxSquare(resultSet.getInt(DEMAND_MAX_SQUARE_COLUMN));
                demand.setMinNumberOfRooms(resultSet.getInt(DEMAND_MIN_COUNT_OF_ROOMS_COLUMN));
                demand.setMaxNumberOfRooms(resultSet.getInt(DEMAND_MAX_COUNT_OF_ROOMS_COLUMN));
                demand.setMinNumberOfFloors(resultSet.getInt(DEMAND_MIN_NUMBER_OF_FLOORS_COLUMN));
                demand.setMaxNumberOfFloors(resultSet.getInt(DEMAND_MAX_NUMBER_OF_FLOORS_COLUMN));
                demand.setMinFloor(resultSet.getInt(DEMAND_MIN_FLOOR));
                demand.setMaxFloor(resultSet.getInt(DEMAND_MAX_FLOOR));

                System.out.println(demand);

                //добавляем потребность в список
                list.add(demand);
                //добавляем ID  потребности в список
                idDemandArray.add(resultSet.getInt(DEMAND_ID_COLUMN));
            }
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка формирования коллекции потребностей",
                    "Возникла ошибка при формировании списка потребностей в доме из набора данных, полученных из базы",
                    Alert.AlertType.ERROR);
        }

        return list;
    }

    /**
     * Метод для формирования ObservableList с объектами Offer из набора данных
     *
     * @param resultSet набор данных из таблицы 'offers'
     * @return ObservableList с объектами класса Offer
     */
    private ObservableList<Offer> createListOfOffers(ResultSet resultSet) {
        ObservableList<Offer> list = FXCollections.observableArrayList();
        idOfferArray = new ArrayList<>();
        try {
            while (resultSet.next()) {

                //Создание объекта "Предложение"
                Offer offer = new Offer(
                        resultSet.getString(OFFER_CLIENT_COLUMN),
                        resultSet.getString(OFFER_REALTOR_COLUMN),
                        resultSet.getString(OFFER_REAL_ESTATE_COLUMN),
                        resultSet.getInt(OFFER_PRICE_COLUMN)
                );

                System.out.println(offer);

                //добавляем предложение в список
                list.add(offer);
                //добавляем ID предложения в список
                idOfferArray.add(resultSet.getInt(OFFER_ID_COLUMN));
            }
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка формирования коллекции предложений",
                    "Возникла ошибка при формировании списка предложений из набора данных, полученных из базы",
                    Alert.AlertType.ERROR);
        }
        return list;
    }

    /**
     * Метод для формирования ObservableList из набора данных
     *
     * @param resultSet Набор данных из таблицы риэлторов
     * @return ObservableList с объектами класса Realtor
     */
    private ObservableList<Realtor> createListOfRealtors(ResultSet resultSet) {
        ObservableList<Realtor> list = FXCollections.observableArrayList();
//        idRealtorsArray = new ArrayList<>();
        try {
            while (resultSet.next()) {
                //создаем нового риэлтора
                Realtor realtor = new Realtor(
                        resultSet.getString("lastName"),
                        resultSet.getString("firstName"),
                        resultSet.getString("middleName"),
                        resultSet.getInt("commissionPart"
                        ));
                //добавляем риэлтора в список
                list.add(realtor);
                //добавляем ID риэлтора в список
//                idRealtorsArray.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка формирования коллекции риэлторов",
                    "Возникла ошибка при формировании списка риэлторов из набора данных, полученных из базы",
                    Alert.AlertType.ERROR);
        }
        return list;
    }

    /**
     * Метод для получения содержимого таблицы из базы данных
     *
     * @param tableName название таблицы в базе данных
     * @return ResultSet - набор данных из таблицы
     */
    private ResultSet getDataFromDB(String tableName) {
        //SQL запрос на выбор всех данных из таблицы
        String selectAll = String.format("SELECT * FROM %s", tableName);

        ResultSet resultSet = null;
        try {
            PreparedStatement ps = new DatabaseHandler().createDbConnection().prepareStatement(selectAll);
            //выполняем запрос и сохраняем полученные значения в resultSet
            resultSet = ps.executeQuery();
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка получения данных из таблицы " + tableName,
                    "Данные не получены из базы. Проверьте подключение к базе!",
                    Alert.AlertType.ERROR);
        }

        return resultSet;
    }


    /**
     * Метод для создания и открытия модального окна
     *
     * @param headerText  текст заголовка
     * @param contentText текст основого содержимого окна
     */
    private void showAlert(String headerText, String contentText, Alert.AlertType alertType) {
        //создание уведомления
        Alert alert = new Alert(alertType);

        //установка названия окна в зависимости от типа модального окна (ERROR или INFORMATION)
        if (alertType == Alert.AlertType.ERROR) {
            //установка названия окна
            alert.setTitle("Уведомление об ошибке");
        } else if (alertType == Alert.AlertType.INFORMATION) {
            //установка названия окна
            alert.setTitle("Уведомление об успешном выполнении");
        }
        //установка заголовка окна
        alert.setHeaderText(headerText);
        //установка текста содержимого окна
        alert.setContentText(contentText);
        //размер окна не изменяемый
        alert.setResizable(false);
        //определене родительского окна
        alert.initOwner(this.tableDeals.getScene().getWindow());
        //установка типа модального окна
        alert.initModality(Modality.WINDOW_MODAL);

        if (alert.getAlertType().equals(Alert.AlertType.ERROR)) {
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/ru/tds/realestateagency/images/warning.png").toString()));
        } else {
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/ru/tds/realestateagency/images/success.png").toString()));
        }

        //отображаем уведомление
        alert.showAndWait();
    }

}
