package com.swayam.demo.miglayout;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class ContactDetailsPanel extends JPanel {

    private final Widgets widgets = new Widgets();

    public ContactDetailsPanel() {
	initWidgetsAndModels();
    }

    private void initWidgetsAndModels() {
	initTitleComboBox();
	initNameTextField();
	initSuffixTextField();
	initFirmTextField();
	initAddressTextArea();
	initPrimaryAddressCheckBox();
	initPFICCheckBox();
	initDomicileComboBox();
	initAddressTypeComboBox();
	initFlagGroupPanel();
	initWorkPhoneTextField();
	initWorkPhone2TextField();
	initHomePhoneTextField();
	initWorkPhoneCountryComboBox();
	initWorkPhone2CountryComboBox();
	initHomePhoneCountryComboBox();
	initCellPhoneTextField();
	initCellPhoneCountryComboBox();
	initFaxTextField();
	initFax1CountryComboBox();
	initFax2CountryComboBox();
	initFax3CountryComboBox();
	initEmailTextField();
	initContactRefTextField();
	initWatermarkForTransmitterTextField();
	initEstimatesFrequencyComboBox();
	initFinalsFrequencyComboBox();
	initFundInvestorParametersPanel();
	initUpdateModeAllRadioButton();
	initOnlyTheCurrentItemRadioButton();
	initInternalContactIdLabel();
	initLayout();
    }

    private void initWorkPhoneCountryComboBox() {
	widgets.workPhoneCountryComboBox = new JComboBox<>();
	widgets.workPhoneCountryComboBox.addItem("11111111111111111");
    }

    private void initWorkPhone2CountryComboBox() {
	widgets.workPhone2CountryComboBox = new JComboBox<>();
	widgets.workPhone2CountryComboBox.addItem("11111111111111111");
    }

    private void initHomePhoneCountryComboBox() {
	widgets.homePhoneCountryComboBox = new JComboBox<>();
	widgets.homePhoneCountryComboBox.addItem("11111111111111111");
    }

    private void initFax1CountryComboBox() {
	widgets.fax1CountryComboBox = new JComboBox<>();
	widgets.fax1CountryComboBox.addItem("11111111111111111");
    }

    private void initFax2CountryComboBox() {
	widgets.fax2CountryComboBox = new JComboBox<>();
	widgets.fax2CountryComboBox.addItem("11111111111111111");
    }

    private void initFax3CountryComboBox() {
	widgets.fax3CountryComboBox = new JComboBox<>();
	widgets.fax3CountryComboBox.addItem("11111111111111111");
    }

    private void initCellPhoneCountryComboBox() {
	widgets.cellPhoneCountryComboBox = new JComboBox<>();
	widgets.cellPhoneCountryComboBox.addItem("11111111111111111");
    }

    private void initTitleComboBox() {
	widgets.titleComboBox = new JComboBox<>();
	widgets.titleComboBox.addItem("Mr.");
    }

    private void initNameTextField() {
	widgets.nameTextField = new JTextField();
    }

    private void initSuffixTextField() {
	widgets.suffixTextField = new JTextField();
    }

    private void initFirmTextField() {
	widgets.firmTextField = new JTextField();
    }

    private void initAddressTextArea() {
	widgets.addressTextArea = new JTextArea();
	widgets.addressTextArea.setBorder(BorderFactory.createEtchedBorder());
    }

    private void initPrimaryAddressCheckBox() {
	widgets.primaryAddressCheckBox = new JCheckBox("ewttwtwt");
    }

    private void initPFICCheckBox() {
	widgets.pficCheckBox = new JCheckBox("jjjfhfh");
    }

    private void initDomicileComboBox() {
	widgets.domicileComboBox = new JComboBox<>();
    }

    private void initAddressTypeComboBox() {
	widgets.addressTypeComboBox = new JComboBox<>();
    }

    private void initFlagGroupPanel() {
	widgets.flagGroupPanel = new JPanel();
	widgets.flagGroupPanel.setLayout(new MigLayout());
	widgets.flagGroupPanel.add(new JCheckBox("abcdef"));
	widgets.flagGroupPanel.setMinimumSize(new Dimension(300, 100));
    }

    private void initWorkPhoneTextField() {
	widgets.workPhoneTextField = new JTextField();
    }

    private void initWorkPhone2TextField() {
	widgets.workPhone2TextField = new JTextField();
    }

    private void initHomePhoneTextField() {
	widgets.homePhoneTextField = new JTextField();
    }

    private void initCellPhoneTextField() {
	widgets.cellPhoneTextField = new JTextField();
    }

    private void initFaxTextField() {
	widgets.faxTextField = new JTextField();

    }

    private void initEmailTextField() {
	widgets.emailTextField = new JTextField();
    }

    private void initContactRefTextField() {
	widgets.contactRefTextField = new JTextField();
    }

    private void initWatermarkForTransmitterTextField() {
	widgets.watermarkForTransmitterTextField = new JTextField();
	widgets.watermarkForTransmitterTextField.setVisible(false);
    }

    private void initEstimatesFrequencyComboBox() {
	widgets.estimatesFrequencyComboBox = new JComboBox<>();
	widgets.estimatesFrequencyComboBox.addItem("1234567");
    }

    private void initFinalsFrequencyComboBox() {
	widgets.finalsFrequencyComboBox = new JComboBox<>();
	widgets.finalsFrequencyComboBox.addItem("123456789");
    }

    private void initFundInvestorParametersPanel() {
	widgets.fundInvestorParametersPanel = new JPanel();
	widgets.fundInvestorParametersPanel.setLayout(new MigLayout());
	widgets.fundInvestorParametersPanel.add(new JCheckBox("gggggggggggg"));
	widgets.fundInvestorParametersPanel.setMinimumSize(new Dimension(200, 100));
    }

    private void initUpdateModeAllRadioButton() {
	widgets.updateModeAllRadioButton = new JRadioButton("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
    }

    private void initOnlyTheCurrentItemRadioButton() {
	widgets.updateModeOnlyTheCurentItemRadioButton = new JRadioButton("llllllllllllllllllllllllllllllllllllllllllllllllll");
    }

    private void initInternalContactIdLabel() {
	widgets.internalContactIdLabel = new JLabel("ABCDEF");
    }

    private void initLayout() {
	// 5 columns
	setLayout(new MigLayout("fill, insets 10 10 10 20", "[fill][fill, grow, 150:150][fill, grow][fill][fill]", "[][][][fill, grow][][][][][fill, grow][]"));

	// ------------------ ROW 1
	// col 1
	add(new JLabel("Name"));
	// col 2
	add(widgets.titleComboBox, "span 2, split, w 70!");
	// col 3
	add(widgets.nameTextField, "pushx");
	// col 4
	add(new JLabel("Suffix"), "w 40!");
	// col 5
	add(widgets.suffixTextField, "w 40!, align right, wrap");

	// ------------------ ROW 2
	// col 1
	add(new JLabel("Firm"));
	// col 2
	add(widgets.firmTextField, "spanx 4, wrap");

	// ------------------ ROW 3
	// col 1
	add(new JLabel("Address"), "spanx 5, wrap");

	// ------------------ ROW 4 : COMPLEX
	// sub-row 1, col 1
	add(createAddressPanel(), "spanx 2, spany 6");

	// sub-row 1, col 2
	add(widgets.primaryAddressCheckBox, "spanx 3, wrap");

	// sub-row 2, col 2
	add(widgets.pficCheckBox, "spanx 3, wrap");

	// sub-row 3, col 2
	add(new JLabel("Domicile"), "spanx 3, wrap");

	// sub-row 4, col 2
	add(widgets.domicileComboBox, "spanx 3, wrap");

	// sub-row 5, col 2
	add(new JLabel("Address Type"), "spanx 3, wrap");
	// sub-row 6, col 2
	add(widgets.addressTypeComboBox, "spanx 3, wrap");

	// ------------------ ROW 5
	// col 1
	add(new JLabel("Work Phone"));
	// col 2
	add(widgets.workPhoneTextField);
	// col 3
	add(createWorkPhoneCountryPanel(), "spanx 3, wrap");

	// ------------------ ROW 6
	// col 1
	add(new JLabel("Work Phone #2"));
	// col 2
	add(widgets.workPhone2TextField);
	// col 3
	add(createWorkPhone2CountryPanel(), "spanx 3, wrap");

	// ------------------ ROW 7
	// col 1
	add(new JLabel("Home Phone"));
	// col 2
	add(widgets.homePhoneTextField);
	// col 3
	add(createHomePhoneCountryPanel(), "spanx 3, wrap");

	// ------------------ ROW 8
	add(new JLabel("Cell Phone"));
	add(widgets.cellPhoneTextField);
	add(createCellPhoneCountryPanel(), "spanx 3, wrap");

	// ------------------ ROW 9 : COMPLEX
	// sub-row 1, col 1
	add(createFaxPanel(), "spanx 2");

	// sub-row 1, col 2
	add(widgets.flagGroupPanel, "top, spanx 3, spany 4, wrap");

	// sub-row 2, col 1
	add(new JLabel("Email"));
	// sub-row 1, col 2
	add(widgets.emailTextField, "wrap");

	// sub-row 3, col 1
	add(new JLabel("Contact REF"), "top");
	// sub-row 3, col 2
	add(widgets.contactRefTextField, "top, wrap");

	// sub-row 4, col 1
	add(new JLabel("Watermark for Transmitter"), "top");
	// sub-row 4, col 2
	add(widgets.watermarkForTransmitterTextField, "top, wrap");

	// ------------------ ROW 10 : COMPLEX
	// sub-row 1, col 1
	add(createNavNotificationPanel(), "spanx 2");
	// sub-row 1, col 2
	add(widgets.fundInvestorParametersPanel, "spanx 3, spany 4, wrap");

	// sub-row 2, col 1
	add(widgets.updateModeOnlyTheCurentItemRadioButton, "spanx 2, wrap");

	// sub-row 3, col 1
	add(widgets.updateModeAllRadioButton, "spanx 2, wrap");

	// sub-row 4, col 1
	add(widgets.internalContactIdLabel, "spanx 2");
    }

    private JPanel createAddressPanel() {
	JPanel addressPanel = new JPanel(new MigLayout("insets 0, fill", "[fill,100][fill,grow]", "[]0[]0[]0[]0[]0[grow]"));
	addressPanel.add(createGrayedLabel("Street 1"), "gaptop 5");
	addressPanel.add(new JScrollPane(widgets.addressTextArea), "span 1 6, wrap, growy");
	addressPanel.add(createGrayedLabel("Street 2"), "wrap");
	addressPanel.add(createGrayedLabel("Street 3"), "wrap");
	addressPanel.add(createGrayedLabel("City, ST Zip"), "wrap");
	addressPanel.add(createGrayedLabel("Country"), "wrap");
	addressPanel.add(new JLabel());
	return addressPanel;
    }

    private JLabel createGrayedLabel(String caption) {
	JLabel aLabel = new JLabel(caption);
	aLabel.setForeground(Color.gray);
	return aLabel;
    }

    private JPanel createFaxPanel() {
	JPanel faxPanel = new JPanel(new MigLayout("fill, wrap 2", "10[]10[fill, grow]0"));
	// row 1
	faxPanel.add(new JLabel("Fax # "));
	faxPanel.add(widgets.faxTextField, "align left");

	// row 2
	faxPanel.add(new JLabel("Fax #1 Country "));
	faxPanel.add(widgets.fax1CountryComboBox, "align left");

	// row 3
	faxPanel.add(new JLabel("Fax #2 Country "));
	faxPanel.add(widgets.fax2CountryComboBox, "align left");

	// row 4
	faxPanel.add(new JLabel("Fax #3 Country "));
	faxPanel.add(widgets.fax3CountryComboBox, "align left");
	return faxPanel;
    }

    private JPanel createNavNotificationPanel() {
	JPanel navNotificationPanel = new JPanel(new MigLayout("fill"));
	navNotificationPanel.add(new JLabel("Estimates"), "right");
	navNotificationPanel.add(widgets.estimatesFrequencyComboBox);
	navNotificationPanel.add(new JLabel("Finals"), "right");
	navNotificationPanel.add(widgets.finalsFrequencyComboBox);
	return navNotificationPanel;
    }

    private JPanel createWorkPhoneCountryPanel() {
	JPanel workPhonePanel = new JPanel(new MigLayout("fill", "0[]10[fill, grow]0", "0[]0"));
	workPhonePanel.add(new JLabel("Work Phone Country"), "w 150!");
	workPhonePanel.add(widgets.workPhoneCountryComboBox);
	return workPhonePanel;
    }

    private JPanel createWorkPhone2CountryPanel() {
	JPanel workPhonePanel = new JPanel(new MigLayout("fill", "0[]10[fill, grow]0", "0[]0"));
	workPhonePanel.add(new JLabel("Work Phone State"), "w 150!");
	workPhonePanel.add(widgets.workPhone2CountryComboBox);
	return workPhonePanel;
    }

    private JPanel createHomePhoneCountryPanel() {
	JPanel workPhonePanel = new JPanel(new MigLayout("fill", "0[]10[fill, grow]0", "0[]0"));
	workPhonePanel.add(new JLabel("Home Phone Country"), "w 150!");
	workPhonePanel.add(widgets.homePhoneCountryComboBox);
	return workPhonePanel;
    }

    private JPanel createCellPhoneCountryPanel() {
	JPanel workPhonePanel = new JPanel(new MigLayout("fill", "0[]10[fill, grow]0", "0[]0"));
	workPhonePanel.add(new JLabel("Cell Phone Country"), "w 150!");
	workPhonePanel.add(widgets.cellPhoneCountryComboBox);
	return workPhonePanel;
    }

    private static class Widgets {
	private JComboBox<String> titleComboBox;
	private JTextField nameTextField;
	private JTextField suffixTextField;
	private JTextField firmTextField;
	private JTextArea addressTextArea;
	private JCheckBox primaryAddressCheckBox;
	private JCheckBox pficCheckBox;
	private JComboBox<String> domicileComboBox;
	private JComboBox<String> addressTypeComboBox;
	private JPanel flagGroupPanel;
	private JTextField workPhoneTextField;
	private JComboBox<String> workPhone2CountryComboBox;
	private JTextField workPhone2TextField;
	private JComboBox<String> workPhoneCountryComboBox;
	private JTextField homePhoneTextField;
	private JComboBox<String> homePhoneCountryComboBox;
	private JTextField cellPhoneTextField;
	private JComboBox<String> cellPhoneCountryComboBox;
	private JTextField faxTextField;
	private JComboBox<String> fax1CountryComboBox;
	private JComboBox<String> fax2CountryComboBox;
	private JComboBox<String> fax3CountryComboBox;
	private JTextField emailTextField;
	private JTextField contactRefTextField;
	private JTextField watermarkForTransmitterTextField;
	private JComboBox<String> estimatesFrequencyComboBox;
	private JComboBox<String> finalsFrequencyComboBox;
	private JPanel fundInvestorParametersPanel;
	private JRadioButton updateModeAllRadioButton;
	private JRadioButton updateModeOnlyTheCurentItemRadioButton;
	private JLabel internalContactIdLabel;
    }

}
