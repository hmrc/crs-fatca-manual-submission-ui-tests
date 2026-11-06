/*
 * Copyright 2026 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ui.specs.manual

import uk.gov.hmrc.ui.pages.*
import uk.gov.hmrc.ui.pages.manual.*
import uk.gov.hmrc.ui.pages.manual.accountholder.*
import uk.gov.hmrc.ui.pages.manual.controllingpersonandsubstantialowners.*
import uk.gov.hmrc.ui.specs.BaseSpec
import uk.gov.hmrc.ui.specs.tags.*
import uk.gov.hmrc.ui.utils.TestData

class CpSoManualSpec extends BaseSpec with ManualJourneyHelper {

  Feature("CRS/FATCA Manual - CP-SO individual or organisation") {

    Scenario(
      "CP-SO - reaching individual name page after selecting Individual (FATCA)",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as FATCA")
      journeyToYourFis()
      selectTheSuitableFI("FourthFI")
      navigateToTaskList("FATCA")

      When("They add an eligible account holder, open the substantial owners task and select the account holder")
      CpSoAccountHolderPage.reachCpSoJourney("FATCA")

      Then("They see the individual or organisation page")
      CpSoIndividualOrOrganisationPage.checkPage()

      When("They select Individual and continue")
      CpSoIndividualOrOrganisationPage.selectIndividualAndContinue()

      Then("They see the individual name page with FATCA wording")
      CpSoIndividualNamePage.checkHeadingForFatca()

      When("They enter a valid first and last name and continue")
      CpSoIndividualNamePage.enterNameAndContinue("Sarah", "Smith")

      Then("They see the have date of birth page with FATCA wording")
      CpSoIndividualHaveDoB.checkPageForFatca()

      When("They select Yes and continue")
      CpSoIndividualHaveDoB.selectYesAndContinue()

      Then("They are taken to the date of birth page")
      CpSoIndividualD0B.checkPage()

      When("They enter a valid date of birth and continue")
      CpSoIndividualD0B.enterDateOfBirth()

      Then("They see the have place of birth page")
      CpSoIndividualHavePlaceOfBirthPage.checkPage()

      When("They select Yes and continue")
      CpSoIndividualHavePlaceOfBirthPage.selectYesAndContinue()

      And("They enter the Individual place of birth")
      CpSoIndividualPlaceOfBirthPage.enterPlaceOfBirthAndContinue("city", "Region", "Japan")

      Then("They need to provide the details of Where are they based")
      CpSoWhereAreTheyBasedPage.checkPage()

      When("They select yes to enter UK address")
      CpSoWhereAreTheyBasedPage.selectYesAndContinue()

      Then("They will see the UK Postcode page")
      CpSoUKPostcodePage.checkPage()

      When("They enter the address and click find address")
      CpSoUKPostcodePage.enterPostcodeAndFindAddress(TestData.postcodeMultipleAddressEnv)

      And("They select the first address and continue")
      CpSoSelectAddressPage.selectFirstAddressAndContinue()

      Then("They lands on resident for tax page")
      CpSoResidentForTaxPage.checkPage()

      When("They enter the resident country for tax and continue")
      CpSoResidentForTaxPage.addResidentForTaxCountry(TestData.residentForTax)

      Then("They lands on tax identification number page")
      CpSoIdentificationNumberPage.checkPage()

      When("They enter the tax identification number and continue")
      CpSoIdentificationNumberPage.enterIdentificationAndContinue("123-45-6789")

      Then("They enter the tax resident countries page")
      CpSoTaxResidentCountriesPage.checkPage()

      When("They click on change link to change the tax resident country")
      CpSoTaxResidentCountriesPage.changeCountry(TestData.residentForTax)

      Then("They enter change resident for tax page")
      CpSoChangeResidentForTaxPage.checkPage()

      And("They want to continue with the same country")
      CpSoChangeResidentForTaxPage.submitPage()

      And("With the same identification number")
      CpSoChangeIdentificationNumberPage.enterIdentificationAndContinue("123-45-6789")

      Then("They enters again to tax resident countries page")
      CpSoChangeTaxResidentCountriesPage.checkPage()

      And("If they don't want to add another country they can continue to check answers page")
      CpSoChangeTaxResidentCountriesPage.selectNoAndContinue()

    }

    Scenario(
      "CP-SO - selecting Organisation continues the journey",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as FATCA")
      journeyToYourFis()
      selectTheSuitableFI("FourthFI")
      navigateToTaskList("FATCA")

      When("They add an eligible account holder, open the substantial owners task and select the account holder")
      CpSoAccountHolderPage.reachCpSoJourney("FATCA")

      Then("They see the individual or organisation page")
      CpSoIndividualOrOrganisationPage.checkPage()

      When("They select Organisation and continue")
      CpSoIndividualOrOrganisationPage.selectOrganisationAndContinue()

      Then("They see the organisation name page")
      CpSoOrganisationNamePage.checkPage()

      When("They enter a valid organisation name and continue")
      CpSoOrganisationNamePage.enterNameAndContinue("ABC Corp")

      Then("They need to provide the details of Where are they based")
      CpSoWhereAreTheyBasedPage.checkOrgPage()

      When("They select yes to enter UK address")
      CpSoWhereAreTheyBasedPage.selectYesAndContinue()

      Then("They will see the UK Postcode page")
      CpSoUKPostcodePage.checkOrgPage()

      When("They want to enter the address manually, they can use the link")
      CpSoUKPostcodePage.enterAddressManually()

      Then("They will see the address UK page")
      CpSoAddressUKPage.checkOrgPage()

      When("They enter the address manually and select Save and continue")
      CpSoAddressUKPage.enterAddressUK()

      Then("They enter resident for tax page")
      CpSoResidentForTaxPage.checkPageForOrganisation()

      When("They enter the resident for tax and continue")
      CpSoResidentForTaxPage.addResidentForTaxCountry(TestData.residentForTax)

      And("They enter the tax idenfication number")
      CpSoIdentificationNumberPage.enterIdentificationAndContinue("123-45-6789")

      Then("They lands on tax resident countries page")
      CpSoTaxResidentCountriesPage.checkPageForOrganisation()

      When("They click on Remove link to remove the country")
      CpSoTaxResidentCountriesPage.removeCountry(TestData.residentForTax)

      Then("They enter remove tax resident country page")
      CpSoChangeRemoveTaxResidentCountryPage.checkPage()

      And("If they select Yes and continue")
      CpSoChangeRemoveTaxResidentCountryPage.selectYesAndContinue()

      Then("They enter to again to tax resident countries page")
      CpSoChangeTaxResidentCountriesPage.checkPageWithNoAddedCountries()

    }

    Scenario(
      "CP-SO - individual name page under CRS regime",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as CRS")
      journeyToYourFis()
      selectTheSuitableFI("FirstFI")
      navigateToTaskList("CRS")

      When("They add an eligible account holder, open the controlling persons task and select the account holder")
      CpSoAccountHolderPage.reachCpSoJourney("CRS")

      Then("They see the individual name page with CRS wording")
      CpSoIndividualNamePage.checkHeadingForCrs()

      When("They enter a valid first and last name and continue")
      CpSoIndividualNamePage.enterNameAndContinue("Sarah", "Smith")

      Then("They see the have date of birth page with CRS wording")
      CpSoIndividualHaveDoB.checkPageForCrs()

      When("They select Yes and continue")
      CpSoIndividualHaveDoB.selectYesAndContinue()

      Then("They are taken to the date of birth page")
      CpSoIndividualD0B.checkPage()

      When("They enter a valid date of birth and continue")
      CpSoIndividualD0B.enterDateOfBirth()

      Then("They see the have place of birth page")
      CpSoIndividualHavePlaceOfBirthPage.checkPage()

      When("They select Yes and continue")
      CpSoIndividualHavePlaceOfBirthPage.selectYesAndContinue()

      And("They enter the Individual place of birth")
      CpSoIndividualPlaceOfBirthPage.enterPlaceOfBirthAndContinue("city", "Region", "Japan")

      Then("They see the controlling person type page")
      CpSoControllingPersonTypePage.checkPage()

      When("They select more than one controlling person type and continue")
      CpSoControllingPersonTypePage.selectTypesAndContinue(
        "Ownership of a legal person",
        "Settlor of a trust",
        "Trustee of a trust"
      )

      Then("They see the self-certification page")
      CpSoSelfCertificationPage.checkPage()

      When("They select Yes and continue")
      CpSoSelfCertificationPage.selectSelfCertificationAndContinue("Yes")

      Then("They see the where are they based page")
      CpSoWhereAreTheyBasedPage.checkPage()

      When("They select Yes to say they are based in the UK and continue")
      CpSoWhereAreTheyBasedPage.selectYesAndContinue()

      Then("They see the UK postcode page")
      CpSoUKPostcodePage.checkPage()

      When("They enter a postcode and find the address")
      CpSoUKPostcodePage.enterPostcodeAndFindAddress(TestData.postcodeMultipleAddressEnv)

      Then("They see the select address page")
      CpSoSelectAddressPage.checkPage()

      When("They select an address and continue")
      CpSoSelectAddressPage.selectFirstAddressAndContinue()

      Then("They lands on resident for tax page")
      CpSoResidentForTaxPage.checkPage()

      When("They enter the resident country for tax and continue")
      CpSoResidentForTaxPage.addResidentForTaxCountry(TestData.residentForTax)

      Then("They lands on have identification number page")
      CpSoHaveIdentificationPage.checkPage()

      When("They select no and continue")
      CpSoHaveIdentificationPage.selectNoAndContinue()

      Then("They lands on tax resident countries page")
      CpSoTaxResidentCountriesPage.checkPage()

      And("If they don't want to add another country")
      CpSoTaxResidentCountriesPage.selectNoAndContinue()

    }

    Scenario(
      "CP-SO - No to date of birth goes to have place of birth (FATCA)",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as FATCA")
      journeyToYourFis()
      selectTheSuitableFI("FourthFI")
      navigateToTaskList("FATCA")

      When("They add an eligible account holder, open the substantial owners task and select the account holder")
      CpSoAccountHolderPage.reachCpSoJourney("FATCA")

      And("They select Individual and enter a valid name")
      CpSoIndividualOrOrganisationPage.selectIndividualAndContinue()
      CpSoIndividualNamePage.enterNameAndContinue("Sarah", "Smith")

      Then("They see the have date of birth page")
      CpSoIndividualHaveDoB.checkPageForFatca()

      When("They select No and continue")
      CpSoIndividualHaveDoB.selectNoAndContinue()

      Then("They see the have place of birth page")
      CpSoIndividualHavePlaceOfBirthPage.checkPage()
    }

    Scenario(
      "CP-SO - page is not accessible under CRS regime",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as CRS")
      journeyToYourFis()
      selectTheSuitableFI("FirstFI")
      navigateToTaskList("CRS")

      When("They navigate directly to the individual or organisation page")
      CpSoIndividualOrOrganisationPage.goToPage()

      Then("They see the page not found error")
      PageNotFoundPage.checkPage()
    }

    Scenario(
      "CP-SO account holder - no account holders shows the empty state and back link (CRS)",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as CRS with no account holders")
      journeyToYourFis()
      selectTheSuitableFI("FirstFI")
      navigateToTaskList("CRS")

      When("They go to the CP/SO account holder page")
      CpSoAccountHolderPage.goToPage()

      Then("They see the empty state with the add an account holder and back links")
      CpSoAccountHolderPage.checkEmptyState("CRS")

      When("They click the back to send a CRS report link")
      CpSoAccountHolderPage.clickBackToSendAReport()
    }

    Scenario(
      "CP-SO account holder - no account holders shows the empty state and add link (FATCA)",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as FATCA with no account holders")
      journeyToYourFis()
      selectTheSuitableFI("FourthFI")
      navigateToTaskList("FATCA")

      When("They go to the CP/SO account holder page")
      CpSoAccountHolderPage.goToPage()

      Then("They see the empty state")
      CpSoAccountHolderPage.checkEmptyState("FATCA")

      /** uncomment the below lines after the implementation of ticket DAC6-4524
        *
        * When("They click the add an account holder link") CpSoAccountHolderPage.clickAddAnAccountHolder()
        *
        * Then("They are taken to the account holder pages") CpSoAccountHolderPage.checkOnAccountHolderPages()
        */

    }

    Scenario(
      "CP-SO account holder - CRS account holder type CRS102 is not eligible",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has added a CRS102 organisation account holder")
      journeyToYourFis()
      selectTheSuitableFI("FirstFI")
      navigateToTaskList("CRS")
      AccountHolderAccountPage.reachIndividualOrOrganisationPageByNumber()
      AccountHolderIndividualOrOrganisationPage.selectOrganisationAndContinue()
      AccountHolderOrganisationNamePage.enterCrsAccountHolderName()
      AccountHolderOrgCrsTypePage.checkPage()
      AccountHolderOrgCrsTypePage.selectAccountHolderType("crsReportable")
      AccountHolderWhereAreTheyBasedPage.checkPage()

      When("They return to the task list and open the controlling persons task")
      AccountHolderAccountPage.goToTaskList()
      CpSoAccountHolderPage.goToPage()

      Then("The account holder is not listed, so they see the empty state")
      CpSoAccountHolderPage.checkEmptyState("CRS")
    }

    Scenario(
      "CP-SO account holder - FATCA account holder type FATCA104 is not eligible",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has added a FATCA104 organisation account holder")
      journeyToYourFis()
      selectTheSuitableFI("FourthFI")
      navigateToTaskList("FATCA")
      AccountHolderAccountPage.reachIndividualOrOrganisationPageByNumber()
      AccountHolderIndividualOrOrganisationPage.selectOrganisationAndContinue()
      AccountHolderOrganisationNamePage.enterFatcaAccountHolderName()
      AccountHolderOrgFatcaTypePage.checkPage()
      AccountHolderOrgFatcaTypePage.selectAccountHolderType("specifiedPerson")
      AccountHolderWhereAreTheyBasedPage.checkPage()

      When("They return to the task list and open the substantial owners task")
      AccountHolderAccountPage.goToTaskList()
      CpSoAccountHolderPage.goToPage()

      Then("The account holder is not listed, so they see the empty state")
      CpSoAccountHolderPage.checkEmptyState("FATCA")
    }
  }
}
