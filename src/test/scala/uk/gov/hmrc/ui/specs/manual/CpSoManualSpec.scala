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
      "CP-SO - end to end (FATCA): accounts, organisation account holder, individual substantial owner, list page, remove and section completed",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as FATCA")
      journeyToYourFis()
      selectTheSuitableFI("FourthFI")
      navigateToTaskList("FATCA")

      When("They complete the accounts section")
      ManualSendAReportIndexPage.clickAccounts()
      AccountHolderJourneyHelper.completeAccountByNumberFatca()
      AccountHolderJourneyHelper.completeAccountsSection(TestData.fourthFi)

      And("They open Account holders, select the account and complete an organisation account holder")
      AccountHolderJourneyHelper.openAccountHoldersAndSelectFirstAccount()
      AccountHolderJourneyHelper.completeOrganisationAccountHolderFatca()

      And("They open the substantial owners task and select the account holder")
      CpSoAccountHolderPage.openCpSoJourneyFromTaskList("FATCA")

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

      Then("They lands on check answers page for the substantial owner")
      CpSoCheckAnswersPage.checkPageFATCA()

      When("They select Continue on the check answers page")
      CpSoCheckAnswersPage.submitPage()

      Then("They see the substantial owner list with one substantial owner and no Incomplete tag")
      CpSoListPage.checkAddedCount("FATCA", 1)
      CpSoListPage.checkNotMarkedIncomplete("Sarah Smith")

      When("They remove the substantial owner but select No")
      CpSoListPage.clickRemove("Sarah Smith")
      CpSoRemovePage.checkPage("FATCA", "Sarah Smith")
      CpSoRemovePage.selectNoAndContinue()

      Then("The substantial owner is still listed")
      CpSoListPage.checkAddedCount("FATCA", 1)

      When("They select No to add another substantial owner and continue")
      CpSoListPage.chooseNoAnotherAndContinue()

      Then("They are on the section completed page")
      CpSoSectionCompletedPage.checkPage("FATCA", TestData.fourthFi)

      When("They select Yes and continue")
      CpSoSectionCompletedPage.selectYesAndContinue()

      Then("They are back on the task list and the CP/SO row shows Completed")
      ManualSendAReportIndexPage.checkCpSoStatus("Completed")

      When("They go back to the list, remove the substantial owner and select Yes")
      CpSoListPage.goToPage()
      CpSoListPage.clickRemove("Sarah Smith")
      CpSoRemovePage.checkPage("FATCA", "Sarah Smith")
      CpSoRemovePage.selectYesAndContinue()

      Then("They see the removed banner and no substantial owners added")
      CpSoListPage.checkRemovedBanner("FATCA", "Sarah Smith")
      CpSoListPage.checkNoneAdded("FATCA")

      When("They go back to the task list")
      AccountHolderAccountPage.goToTaskList()

      Then("The substantial owners row shows <status>")
      ManualSendAReportIndexPage.checkCpSoStatus("Completed")
    }

    Scenario(
      "CP-SO - end to end (FATCA): organisation substantial owner, UK address, tax residency, check answers and list",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as FATCA")
      journeyToYourFis()
      selectTheSuitableFI("FourthFI")
      navigateToTaskList("FATCA")

      When("They complete the accounts section")
      ManualSendAReportIndexPage.clickAccounts()
      AccountHolderJourneyHelper.completeAccountByNumberFatca()
      AccountHolderJourneyHelper.completeAccountsSection(TestData.fourthFi)

      And("They open Account holders, select the account and complete an organisation account holder")
      AccountHolderJourneyHelper.openAccountHoldersAndSelectFirstAccount()
      AccountHolderJourneyHelper.completeOrganisationAccountHolderFatca()

      And("They open the substantial owners task and select the account holder")
      CpSoAccountHolderPage.openCpSoJourneyFromTaskList("FATCA")

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

      Then("They enter to again to tax resident countries page with no countries added")
      CpSoChangeTaxResidentCountriesPage.checkPageWithNoAddedCountries()

      When("They add the country again")
      CpSoChangeTaxResidentCountriesPage.clickAddCountry()

      Then("They see the change resident for tax page for the organisation")
      CpSoChangeResidentForTaxPage.checkPageForOrganisation()

      When("They enter the resident for tax country and continue")
      CpSoChangeResidentForTaxPage.addResidentForTaxCountry(TestData.residentForTax)

      And("They enter the tax identification number")
      CpSoChangeIdentificationNumberPage.enterIdentificationAndContinue("123-45-6789")

      When("They select No to add another country and continue")
      CpSoChangeTaxResidentCountriesPage.selectNoAndContinue()

      Then("They land on the check answers page for the organisation substantial owner")
      CpSoCheckAnswersPage.checkPageFATCA()

      When("They select Continue on the check answers page")
      CpSoCheckAnswersPage.submitPage()

      Then("The list shows the organisation as a substantial owner with no Incomplete tag")
      CpSoListPage.checkAddedCount("FATCA", 1)
      CpSoListPage.checkNotMarkedIncomplete("ABC Corp")

      When("They select No to add another substantial owner and continue")
      CpSoListPage.chooseNoAnotherAndContinue()

      Then("They are on the section completed page")
      CpSoSectionCompletedPage.checkPage("FATCA", TestData.fourthFi)

      When("They select Yes and continue")
      CpSoSectionCompletedPage.selectYesAndContinue()

      Then("They are back on the task list and the CP/SO row shows Completed")
      ManualSendAReportIndexPage.checkCpSoStatus("Completed")
    }

    Scenario(
      "CP-SO - end to end (CRS): accounts, organisation account holder, individual controlling person, list page, remove and section completed",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as CRS")
      journeyToYourFis()
      selectTheSuitableFI("FirstFI")
      navigateToTaskList("CRS")

      When("They complete the accounts section")
      ManualSendAReportIndexPage.clickAccounts()
      AccountHolderJourneyHelper.completeAccountByNumberCrs()
      AccountHolderJourneyHelper.completeAccountsSection(TestData.firstFi)

      And("They open Account holders, select the account and complete an organisation account holder")
      AccountHolderJourneyHelper.openAccountHoldersAndSelectFirstAccount()
      AccountHolderJourneyHelper.completeOrganisationAccountHolderCrs()

      And("They open the controlling persons task and select the account holder")
      CpSoAccountHolderPage.openCpSoJourneyFromTaskList("CRS")

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

      And("If they don't want to add another country and continue")
      CpSoTaxResidentCountriesPage.selectNoAndContinue()

      Then("They lands on check answers page for the controlling person")
      CpSoCheckAnswersPage.checkPageCRS()

      When("They select Continue on the check answers page")
      CpSoCheckAnswersPage.submitPage()

      Then("They see the controlling person list with one controlling person and no Incomplete tag")
      CpSoListPage.checkAddedCount("CRS", 1)
      CpSoListPage.checkNotMarkedIncomplete("Sarah Smith")

      When("They remove the controlling person but select No")
      CpSoListPage.clickRemove("Sarah Smith")
      CpSoRemovePage.checkPage("CRS", "Sarah Smith")
      CpSoRemovePage.selectNoAndContinue()

      Then("The controlling person is still listed")
      CpSoListPage.checkAddedCount("CRS", 1)

      When("They select No to add another controlling person and continue")
      CpSoListPage.chooseNoAnotherAndContinue()

      Then("They are on the section completed page")
      CpSoSectionCompletedPage.checkPage("CRS", TestData.firstFi)

      When("They select Yes and continue")
      CpSoSectionCompletedPage.selectYesAndContinue()

      Then("They are back on the task list and the CP/SO row shows Completed")
      ManualSendAReportIndexPage.checkCpSoStatus("Completed")

      When("They go back to the list, remove the controlling person and select Yes")
      CpSoListPage.goToPage()
      CpSoListPage.clickRemove("Sarah Smith")
      CpSoRemovePage.checkPage("CRS", "Sarah Smith")
      CpSoRemovePage.selectYesAndContinue()

      Then("They see the removed banner and no controlling persons added")
      CpSoListPage.checkRemovedBanner("CRS", "Sarah Smith")
      CpSoListPage.checkNoneAdded("CRS")

      And("The account holder is missing a controlling person")
      CpSoListPage.checkAccountHolderMissingControllingPerson()

      When("They select the back to send a CRS report link")
      CpSoListPage.clickBackToSendAReport()

      Then("They are on the task list and the controlling persons row shows Incomplete")
      ManualSendAReportIndexPage.checkCpSoStatus("Incomplete")
    }

    Scenario(
      "CP-SO - No to date of birth goes to have place of birth - section incomplete (FATCA)",
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

      Then("The unfinished substantial owner is listed as Incomplete")
      CpSoListPage.goToPage()
      CpSoListPage.checkAddedCount("FATCA", 1)
      CpSoListPage.checkMarkedIncomplete("Sarah Smith")
    }

    Scenario(
      "CP-SO - several account holders and controlling persons: missing text, Incomplete tags, plural heading and add another question (CRS)",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has two accounts, each with an eligible organisation account holder, as CRS")
      journeyToYourFis()
      selectTheSuitableFI("FirstFI")
      navigateToTaskList("CRS")
      AccountHolderAccountPage.addAccountByNumber()
      AccountHoldersPage.addAnotherAccountByNumber(TestData.iBANAccount2)
      AccountHoldersPage.addOrganisationAccountHolder("CRS", TestData.iBANAccount)

      And("They add a second account holder from the account holders list, for the second account")
      AccountHolderAccountPage.goToTaskList()
      AccountHolderAccountPage.clickAccountHolders()
      AccountHoldersPage.checkPage()
      AccountHoldersPage.clickAddAnAccountHolder()
      AccountHolderAccountPage.checkPage()
      AccountHolderAccountPage.selectAccountAndContinue(TestData.iBANAccount2)
      AccountHolderIndividualOrOrganisationPage.selectOrganisationAndContinue()
      AccountHolderOrganisationNamePage.enterCrsAccountHolderName()
      AccountHolderOrgCrsTypePage.checkPage()
      AccountHolderOrgCrsTypePage.selectAccountHolderType("passiveWithMultiplePersons")
      AccountHolderWhereAreTheyBasedPage.checkPage()

      And("The account holders list shows both account holders")
      AccountHolderAccountPage.goToTaskList()
      AccountHolderAccountPage.clickAccountHolders()
      AccountHoldersPage.checkHeading(2)
      AccountHolderAccountPage.goToTaskList()

      When("They open the controlling persons task and see both account holders")
      CpSoAccountHolderPage.openFromTaskList()
      CpSoAccountHolderPage.checkAccountHoldersListed("CRS")
      CpSoAccountHolderPage.checkCheckboxCount(2)

      And("They select the first account holder and add a controlling person with just a name")
      CpSoAccountHolderPage.selectFirstAccountHolderAndContinue()
      CpSoIndividualNamePage.checkHeadingForCrs()
      CpSoIndividualNamePage.enterNameAndContinue("Sarah", "Smith")
      CpSoIndividualHaveDoB.checkPageForCrs()

      Then("The list shows one Incomplete controlling person and the other account holder is missing one")
      CpSoListPage.goToPage()
      CpSoListPage.checkAddedCount("CRS", 1)
      CpSoListPage.checkMarkedIncomplete("Sarah Smith")
      CpSoListPage.checkAccountHolderIsMissingControllingPerson()

      And("The add another controlling person question is not shown")
      CpSoListPage.checkAddAnotherQuestionNotShown("CRS")

      When("They add a controlling person and link them to the account holders listed, with just a name")
      CpSoListPage.clickAddButton("CRS")
      CpSoAccountHolderPage.checkPage("CRS")
      CpSoAccountHolderPage.selectAllAccountHoldersAndContinue()
      CpSoIndividualNamePage.enterNameAndContinue("Alex", "Jones")
      CpSoIndividualHaveDoB.checkPageForCrs()

      Then("The list shows two Incomplete controlling persons with the plural heading")
      CpSoListPage.goToPage()
      CpSoListPage.checkAddedCount("CRS", 2)
      CpSoListPage.checkMarkedIncomplete("Sarah Smith")
      CpSoListPage.checkMarkedIncomplete("Alex Jones")

      And("No account holder is missing a controlling person and the add another question is shown")
      CpSoListPage.checkNoAccountHolderIsMissingControllingPerson()
      CpSoListPage.checkAddAnotherQuestionShown("CRS")
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

      When("They click the add an account holder link")
      CpSoAccountHolderPage.clickAddAnAccountHolder()

      Then("They are taken to the account holders page with no account holders")
      AccountHoldersPage.checkPage()
      AccountHoldersPage.checkHeading(0)
      AccountHoldersPage.checkAddAnAccountHolderButtonShown()
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

      CpSoAccountHolderPage.addAccountHolder("CRS", "crsReportable")
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

      CpSoAccountHolderPage.addAccountHolder("FATCA", "specifiedPerson")
      CpSoAccountHolderPage.goToPage()

      Then("The account holder is not listed, so they see the empty state")
      CpSoAccountHolderPage.checkEmptyState("FATCA")
    }
  }
}
