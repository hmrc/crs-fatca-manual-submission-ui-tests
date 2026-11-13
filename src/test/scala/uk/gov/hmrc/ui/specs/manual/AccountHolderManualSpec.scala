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
import uk.gov.hmrc.ui.specs.BaseSpec
import uk.gov.hmrc.ui.specs.tags.*
import uk.gov.hmrc.ui.utils.TestData

class AccountHolderManualSpec extends BaseSpec with ManualJourneyHelper {

  Feature("CRS/FATCA Manual - Account Holder individual name") {

    Scenario(
      "Account Holder - selecting Individual continues the journey - CRS (account number route)",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as CRS")
      journeyToYourFis()
      selectTheSuitableFI("FirstFI")
      navigateToTaskList("CRS")

      When("They open the Account holders task from the task list")
      AccountHolderAccountPage.clickAccountHolders()

      Then("They are on the accounts held by this account holder page with no accounts")
      AccountHolderAccountPage.checkPage()

      When("They click the add an account link")
      AccountHolderAccountPage.clickAddAnAccountLink()

      And("They click the Add an account button")
      AccountHolderAccountPage.clickAddAnAccountButton()

      And("They complete the accounts journey (number route) from the have number page")
      AccountHolderJourneyHelper.completeAccountByNumberCrs()

      /** TODO: after the account journey the user lands on /manual/accounts, which asks "Do you need to add another
        * account?". Select No and continue from there. Until that page is built, No goes to the Under construction
        * page. For now the scenario goes back to the task list and opens Account holders instead.
        */

      And("They open Account holders and select the account")
      AccountHolderJourneyHelper.openAccountHoldersAndSelectFirstAccount()

      Then("They are on the individual or organisation page")
      AccountHolderIndividualOrOrganisationPage.checkPage()

      When("They select Individual and continue")
      AccountHolderIndividualOrOrganisationPage.selectIndividualAndContinue()

      Then("They are on the individual name page")
      AccountHolderIndividualNamePage.checkPage()

      When("They enter a valid first and last name and continue")
      AccountHolderIndividualNamePage.enterNameAndContinue("Sarah", "Smith")

      Then("Navigate the account holder to have date of birth page")
      AccountHolderIndividualHaveDoB.checkPage()

      And("Select Yes or No on Date of birth for the account holder")
      AccountHolderIndividualHaveDoB.selectYesAndContinue()

      Then("They enter a valid date of birth and continue")
      AccountHolderIndividualD0B.enterDateOfBirth()

      Then("They are on the have place of birth page")
      IndividualHavePlaceOfBirthPage.checkPage()

      When("They select Yes and continue")
      IndividualHavePlaceOfBirthPage.selectYesAndContinue()

      Then("They lands on individual place of birth page")
      AccountHolderIndividualPlaceOfBirthPage.checkPage()

      And("They enter the Individual place of birth")
      AccountHolderIndividualPlaceOfBirthPage.enterPlaceOfBirthAndContinue("city", "Region", "Japan")

      Then("They are on the where are they based page")
      AccountHolderWhereAreTheyBasedPage.checkPage()

      When("They select No and continue")
      AccountHolderWhereAreTheyBasedPage.selectNoAndContinue()

      Then("They lands on addressNon-UK page")
      AccountHolderAddressNonUKPage.checkPage()

      When("They enter the non UK address and continue")
      AccountHolderAddressNonUKPage.enterAddressNonUK()

      Then("CRS asks whether a valid self-certification was provided")
      AccountHolderSelfCertificationPage.checkPage()

      When("They select Yes and continue")
      AccountHolderSelfCertificationPage.selectSelfCertificationAndContinue("Yes")

      Then("They are on the resident for tax page")
      AccountHolderResidentForTaxPage.checkPage()

      When("They select the country they are resident for tax and continue")
      AccountHolderResidentForTaxPage.selectCountryAndContinue("Japan")

      Then("CRS asks whether they have a tax identification number")
      AccountHolderHaveIdentificationNumberPage.checkPage()

      When("They select Yes and continue")
      AccountHolderHaveIdentificationNumberPage.selectYesAndContinue()

      Then("They are on the tax identification number page for that country")
      AccountHolderIdentificationNumberPage.checkPageForCountry("Japan")

      When("They enter a tax identification number and continue")
      AccountHolderIdentificationNumberPage.enterTinAndContinue("1234567890")

      Then("They lands on tax resident countries page")
      AccountHolderTaxResidentCountriesPage.checkPage()

      When("They click remove link to remove country")
      AccountHolderTaxResidentCountriesPage.removeCountry("Japan")

      Then("They will be navigated to remove tax resident country page")
      AccountHolderRemoveTaxResidentCountryPage.checkPage()

      When("They select yes and continue")
      AccountHolderRemoveTaxResidentCountryPage.selectYesAndContinue()

      Then("They will lands again on tax resident countries page")
      AccountHolderTaxResidentCountriesPage.checkPageForNoAddedCountries()

    }

    Scenario(
      "Account Holder - selecting Individual continues the journey - FATCA (account identifier route) - then account holders page: add another, Incomplete tag and remove",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as FATCA")
      journeyToYourFis()
      selectTheSuitableFI("FourthFI")
      navigateToTaskList("FATCA")

      When("They open the Account holders task from the task list")
      AccountHolderAccountPage.clickAccountHolders()

      Then("They are on the accounts held by this account holder page with no accounts")
      AccountHolderAccountPage.checkPage()

      When("They click the add an account link")
      AccountHolderAccountPage.clickAddAnAccountLink()

      And("They click the Add an account button")
      AccountHolderAccountPage.clickAddAnAccountButton()

      And("They complete the accounts journey (identifier route) from the have number page")
      AccountHolderJourneyHelper.completeAccountByIdentifierFatca()

      /** TODO: after the account journey the user lands on /manual/accounts, which asks "Do you need to add another
        * account?". Select No and continue from there. Until that page is built, No goes to the Under construction
        * page. For now the scenario goes back to the task list and opens Account holders instead.
        */

      And("They open Account holders and select the account")
      AccountHolderJourneyHelper.openAccountHoldersAndSelectFirstAccount()

      Then("They are on the individual or organisation page")
      AccountHolderIndividualOrOrganisationPage.checkPage()

      When("They select Individual and continue")
      AccountHolderIndividualOrOrganisationPage.selectIndividualAndContinue()

      Then("They are on the individual name page")
      AccountHolderIndividualNamePage.checkPage()

      When("They enter a valid first and last name and continue")
      AccountHolderIndividualNamePage.enterNameAndContinue("Sarah", "Smith")

      Then("Navigate the account holder to have date of birth page")
      AccountHolderIndividualHaveDoB.checkPage()

      And("Select Yes or No on Date of birth for the account holder")
      AccountHolderIndividualHaveDoB.selectYesAndContinue()

      Then("They enter a valid date of birth and continue")
      AccountHolderIndividualD0B.enterDateOfBirth()

      Then("They are on the have place of birth page")
      IndividualHavePlaceOfBirthPage.checkPage()

      When("They select No and continue")
      IndividualHavePlaceOfBirthPage.selectNoAndContinue()

      Then("They are on the where are they based page")
      AccountHolderWhereAreTheyBasedPage.checkPage()

      When("They select Yes and continue")
      AccountHolderWhereAreTheyBasedPage.selectYesAndContinue()

      Then("They are on UK postcode page")
      AccountHolderUKPostcodePage.checkPage()

      When("They enter the postcode and search for the address")
      AccountHolderUKPostcodePage.enterPostcodeAndContinueForAddress(TestData.postcodeMultipleAddress)

      Then("They lands on the multiple addresses page for the given postcode")
      AccountHolderSelectAddressPage.checkPage()

      When("They select the enter address manually link")
      AccountHolderSelectAddressPage.enterAddressManually()

      Then("They will take to address-uk page to enter the address manually")
      AccountHolderAddressUKPage.checkPage()

      And("They can enter the address manually")
      AccountHolderAddressUKPage.enterAddressUK()

      Then("They are on the resident for tax page")
      AccountHolderResidentForTaxPage.checkPage()

      When("They select a non-US country they are resident for tax and continue")
      AccountHolderResidentForTaxPage.selectCountryAndContinue("Japan")

      Then("They are on the tax identification number page for that country")
      AccountHolderIdentificationNumberPage.checkPageForCountry("Japan")

      When("They enter a tax identification number and continue")
      AccountHolderIdentificationNumberPage.enterTinAndContinue("123456789")

      Then("They lands on tax resident countries page")
      AccountHolderTaxResidentCountriesPage.checkPage()

      When("They click remove link to remove country")
      AccountHolderTaxResidentCountriesPage.removeCountry("Japan")

      Then("They will be navigated to remove tax resident country page")
      AccountHolderRemoveTaxResidentCountryPage.checkPage()

      When("They select no and continue to tax resident countries page")
      AccountHolderRemoveTaxResidentCountryPage.selectNoAndContinue()

      And("They select No on tax resident countries page")
      AccountHolderTaxResidentCountriesPage.selectNoAndContinue()

      Then("They lands on check answers page")
      AccountHolderCheckAnswersPage.checkPage()

      When("They select Continue on the check answers page")
      AccountHoldersPage.continueFromCheckAnswers()

      Then(
        "They see the account holders page with the 1 account holder heading and the add another account holder question"
      )
      AccountHoldersPage.checkPage()
      AccountHoldersPage.checkHeading(1)
      AccountHoldersPage.checkAddAnotherQuestionShown()

      When("They select Yes to add another account holder")
      AccountHoldersPage.selectYesToAddAnotherAndContinue()

      Then("They see the accounts held by this account holder page")
      AccountHolderAccountPage.checkPage()

      When("They add a second account holder with just a name and open the account holders link")
      AccountHoldersPage.addIndividualAccountHolderToFirstAccount()
      AccountHolderAccountPage.clickAccountHolders()

      Then("They see the 2 account holders heading with the Incomplete tag on the unfinished account holder only")
      AccountHoldersPage.checkPage()
      AccountHoldersPage.checkHeading(2)
      AccountHoldersPage.checkIncompleteTagCount(1)
      AccountHoldersPage.checkChangeAndRemoveLinks()

      When("They select Remove against the unfinished account holder, Yes and continue")
      AccountHoldersPage.clickRemove(AccountHoldersPage.secondAccountHolderName)
      AccountHolderRemovePage.checkPage("FATCA")
      AccountHolderRemovePage.selectYesAndContinue()

      Then("They see the success banner and one account holder left")
      AccountHoldersPage.checkPage()
      AccountHoldersPage.checkRemovedBanner(AccountHoldersPage.secondAccountHolderName)
      AccountHoldersPage.checkHeading(1)
      AccountHoldersPage.checkAccountHolderNotListed(AccountHoldersPage.secondAccountHolderName)

    }

    Scenario(
      "Account Holder - selecting Organisation continues the journey - FATCA (account number route) - then account holders page: Change, Remove No and Yes, back link and add an account holder",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as FATCA")
      journeyToYourFis()
      selectTheSuitableFI("FourthFI")
      navigateToTaskList("FATCA")

      When("They open the Account holders task from the task list")
      AccountHolderAccountPage.clickAccountHolders()

      Then("They are on the accounts held by this account holder page with no accounts")
      AccountHolderAccountPage.checkPage()

      When("They click the add an account link")
      AccountHolderAccountPage.clickAddAnAccountLink()

      And("They click the Add an account button")
      AccountHolderAccountPage.clickAddAnAccountButton()

      And("They complete the accounts journey (number route) from the have number page")
      AccountHolderJourneyHelper.completeAccountByNumberFatca()

      /** TODO: after the account journey the user lands on /manual/accounts, which asks "Do you need to add another
        * account?". Select No and continue from there. Until that page is built, No goes to the Under construction
        * page. For now the scenario goes back to the task list and opens Account holders instead.
        */

      And("They open Account holders and select the account")
      AccountHolderJourneyHelper.openAccountHoldersAndSelectFirstAccount()

      Then("They are on the individual or organisation page")
      AccountHolderIndividualOrOrganisationPage.checkPage()

      When("They select Organisation and continue")
      AccountHolderIndividualOrOrganisationPage.selectOrganisationAndContinue()

      And("They provide the organisation Name and continue")
      AccountHolderOrganisationNamePage.enterFatcaAccountHolderName()

      Then("They are on organisation FATCA type page")
      AccountHolderOrgFatcaTypePage.checkPage()

      And("They selects the organisation FATCA type and continue")
      AccountHolderOrgFatcaTypePage.selectAccountHolderType("specifiedPerson")

      Then("They will lands on where are they based page")
      AccountHolderWhereAreTheyBasedPage.checkPage()

      When("They select no on where are they based and continue ")
      AccountHolderWhereAreTheyBasedPage.selectNoAndContinue()

      Then("They lands on address non uk page ")
      AccountHolderAddressNonUKPage.checkPage()

      And("They provides the address non uk and continue")
      AccountHolderAddressNonUKPage.enterAddressNonUK()

      Then("They are on the resident for tax page")
      AccountHolderResidentForTaxPage.checkPage()

      When("They select the United States and continue")
      AccountHolderResidentForTaxPage.selectCountryAndContinue("United States")

      Then("They are on the tax identification number page for the United States")
      AccountHolderIdentificationNumberPage.checkPageForCountry("United States")

      When("They enter a valid US tax identification number and continue")
      AccountHolderIdentificationNumberPage.enterTinAndContinue("123-45-6789")

      Then("They lands on tax resident countries page")
      AccountHolderTaxResidentCountriesPage.checkPageForFATCAOrg()

      When("They select No on tax resident countries page")
      AccountHolderTaxResidentCountriesPage.selectNoAndContinue()

      Then("They lands on check answers page")
      AccountHolderCheckAnswersPage.checkPage()

      When("They select Continue on the check answers page")
      AccountHoldersPage.continueFromCheckAnswers()

      Then(
        "They see the account holders page with the 1 account holder heading, Change and Remove links and no Incomplete tag"
      )
      AccountHoldersPage.checkPage()
      AccountHoldersPage.checkHeading(1)
      AccountHoldersPage.checkChangeAndRemoveLinks()
      AccountHoldersPage.checkIncompleteTagCount(0)

      When("They select Change against the account holder")
      AccountHoldersPage.clickChange()

      Then("They see the check answers page again")
      AccountHolderCheckAnswersPage.checkPage()

      When("They select Continue and then Remove against the account holder")
      AccountHoldersPage.continueFromCheckAnswers()
      AccountHoldersPage.clickRemove()

      Then("They see the remove account holder page with the substantial owner wording and the account holder name")
      AccountHolderRemovePage.checkPage("FATCA")

      When("They select No and continue")
      AccountHolderRemovePage.selectNoAndContinue()

      Then("They are back on the account holders page with no banner and the account holder still listed")
      AccountHoldersPage.checkPage()
      AccountHoldersPage.checkNoBanner()
      AccountHoldersPage.checkHeading(1)

      When("They select Remove again, select Yes and continue")
      AccountHoldersPage.clickRemove()
      AccountHolderRemovePage.selectYesAndContinue()

      Then("They see the success banner, the no account holders version and the FATCA back link")
      AccountHoldersPage.checkPage()
      AccountHoldersPage.checkRemovedBanner()
      AccountHoldersPage.checkNoAccountHolders()
      AccountHoldersPage.checkAddAnotherQuestionNotShown()
      AccountHoldersPage.checkBackLink("FATCA", AccountHoldersPage.fiName("FATCA"))

      When("They select the Add an account holder button")
      AccountHoldersPage.clickAddAnAccountHolder()

      Then("They see the accounts held by this account holder page")
      AccountHolderAccountPage.checkPage()

    }

    Scenario(
      "Account Holder - selecting Organisation continues the journey - CRS (Debt or equity account, account identifier route) - then account holders page: Remove No and add another No",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as CRS")
      journeyToYourFis()
      selectTheSuitableFI("ThirdFI")
      navigateToTaskList("CRS")

      When("They open the Account holders task from the task list")
      AccountHolderAccountPage.clickAccountHolders()

      Then("They are on the accounts held by this account holder page with no accounts")
      AccountHolderAccountPage.checkPage()

      When("They click the add an account link")
      AccountHolderAccountPage.clickAddAnAccountLink()

      And("They click the Add an account button")
      AccountHolderAccountPage.clickAddAnAccountButton()

      And("They complete the accounts journey (Debt or equity, identifier route) from the have number page")
      AccountHolderJourneyHelper.completeAccountByDebtOrEquityIdentifierCrs()

      /** TODO: after the account journey the user lands on /manual/accounts, which asks "Do you need to add another
        * account?". Select No and continue from there. Until that page is built, No goes to the Under construction
        * page. For now the scenario goes back to the task list and opens Account holders instead.
        */

      And("They open Account holders and select the account")
      AccountHolderJourneyHelper.openAccountHoldersAndSelectFirstAccount()

      Then("They are on the individual or organisation page")
      AccountHolderIndividualOrOrganisationPage.checkPage()

      When("They select Organisation and continue")
      AccountHolderIndividualOrOrganisationPage.selectOrganisationAndContinue()

      And("They provide the organisation Name and continue")
      AccountHolderOrganisationNamePage.enterCrsAccountHolderName()

      Then("They are on organisation CRS type page")
      AccountHolderOrgCrsTypePage.checkPage()

      And("They selects the organisation CRS type and continue")
      AccountHolderOrgCrsTypePage.selectAccountHolderType("crsReportable")

      Then("They will lands on where are they based page")
      AccountHolderWhereAreTheyBasedPage.checkPage()

      When("They select yes on where are they based and continue ")
      AccountHolderWhereAreTheyBasedPage.selectYesAndContinue()

      Then("They lands on UK postcode page")
      AccountHolderUKPostcodePage.checkPage()

      When("They enter the postcode and search for the address")
      AccountHolderUKPostcodePage.enterPostcodeAndContinueForAddress(TestData.postcodeSingleAddress)

      Then("They lands on Is this your address page")
      AccountHolderIsThisTheAddressPage.checkPage()

      When("They select the address Yes and continue")
      AccountHolderIsThisTheAddressPage.selectYesAndContinue()

      Then("The Debt or equity account means they are asked whether to provide the equity interest type")
      AccountHolderProvideEquityInterestTypePage.checkPage()

      When("They select Yes and continue")
      AccountHolderProvideEquityInterestTypePage.selectYesAndContinue()

      Then("They are on the equity interest type page")
      AccountHolderEquityInterestTypePage.checkPage()

      When("They select more than one equity interest type and continue")
      AccountHolderEquityInterestTypePage.selectTypesAndContinue("Settlor of a trust", "Beneficiary of a trust")

      Then("CRS asks whether a valid self-certification was provided")
      AccountHolderSelfCertificationPage.checkPage()

      When("They select Yes and continue")
      AccountHolderSelfCertificationPage.selectSelfCertificationAndContinue("Yes")

      Then("They are on the resident for tax page")
      AccountHolderResidentForTaxPage.checkPage()

      When("They select the country they are resident for tax and continue")
      AccountHolderResidentForTaxPage.selectCountryAndContinue("United Kingdom")

      Then("CRS asks whether they have a tax identification number")
      AccountHolderHaveIdentificationNumberPage.checkPage()

      When("They select Yes and continue")
      AccountHolderHaveIdentificationNumberPage.selectYesAndContinue()

      Then("They are on the tax identification number page for that country")
      AccountHolderIdentificationNumberPage.checkPageForCountry("United Kingdom")

      When("They enter a tax identification number and continue")
      AccountHolderIdentificationNumberPage.enterTinAndContinue("1234567890")

      Then("They lands on tax resident countries page")
      AccountHolderTaxResidentCountriesPage.checkPageForCRSOrg()

      When("They select No on tax resident countries page")
      AccountHolderTaxResidentCountriesPage.selectNoAndContinue()

      Then("They lands on check answers page")
      AccountHolderCheckAnswersPage.checkPage()

      When("They select Continue on the check answers page")
      AccountHoldersPage.continueFromCheckAnswers()

      Then("They see the account holders page with the 1 account holder heading")
      AccountHoldersPage.checkPage()
      AccountHoldersPage.checkHeading(1)

      When("They select Remove against the account holder")
      AccountHoldersPage.clickRemove()

      Then("They see the remove account holder page with the controlling person wording and the account holder name")
      AccountHolderRemovePage.checkPage("CRS")

      When("They select No and continue")
      AccountHolderRemovePage.selectNoAndContinue()

      Then(
        "They are back on the account holders page with no banner, the account holder still listed and the add another question"
      )
      AccountHoldersPage.checkPage()
      AccountHoldersPage.checkNoBanner()
      AccountHoldersPage.checkHeading(1)
      AccountHoldersPage.checkAddAnotherQuestionShown()

      When("They select No to add another account holder")
      AccountHoldersPage.selectNoToAddAnotherAndContinue()

      Then("They see the under construction page")
      AccountHoldersPage.checkUnderConstruction()
    }

    Scenario(
      "Account Holder - Account Holders link with no accounts shows the accounts held page - CRS",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as CRS and has not added any accounts")
      journeyToYourFis()
      selectTheSuitableFI("FirstFI")
      navigateToTaskList("CRS")

      When("They select the Account holders link")
      AccountHolderAccountPage.clickAccountHolders()

      Then("They see the accounts held page with only the back to send a report link")
      AccountHolderAccountPage.checkNoAccountsState("CRS")
    }

    Scenario(
      "Account Holder - back to send a report link returns to the task list when no accounts - FATCA",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as FATCA and has not added any accounts")
      journeyToYourFis()
      selectTheSuitableFI("FourthFI")
      navigateToTaskList("FATCA")

      When("They select the Account holders link")
      AccountHolderAccountPage.clickAccountHolders()

      Then("They see the accounts held page with FATCA wording on the back link")
      AccountHolderAccountPage.checkNoAccountsState("FATCA")

      When("They select the back to send a report link")
      AccountHolderAccountPage.clickBackToSendAReport()
    }

    Scenario(
      "Account Holder - one account without an account holder shows the missing account text, Incomplete tag and back link - CRS",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has two accounts and one account holder linked to the first account as CRS")
      journeyToYourFis()
      selectTheSuitableFI("FirstFI")
      navigateToTaskList("CRS")
      AccountHolderAccountPage.addAccountByNumber()
      AccountHoldersPage.addAnotherAccountByNumber(TestData.iBANAccount2)
      AccountHoldersPage.addOrganisationAccountHolder("CRS", TestData.iBANAccount)

      When("They open the account holders page")
      AccountHolderAccountPage.clickAccountHolders()
      AccountHoldersPage.checkPage()

      Then(
        "They see the missing account holder text for the second account, the Add an account holder button, the Incomplete tag and the back link"
      )
      AccountHoldersPage.checkHeading(1)
      AccountHoldersPage.checkOneAccountMissing(TestData.iBANAccount2)
      AccountHoldersPage.checkIncompleteTagShown()
      AccountHoldersPage.checkBackLink("CRS", AccountHoldersPage.fiName("CRS"))

      When("They select the back link")
      AccountHoldersPage.clickBackToSendAReport()
    }

    Scenario(
      "Account Holder - multiple accounts without an account holder shows the missing accounts text - CRS",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has three accounts and one account holder linked to the first account as CRS")
      journeyToYourFis()
      selectTheSuitableFI("FirstFI")
      navigateToTaskList("CRS")
      AccountHolderAccountPage.addAccountByNumber()
      AccountHoldersPage.addAnotherAccountByNumber(TestData.iBANAccount2)
      AccountHoldersPage.addAnotherAccountByNumber(TestData.iBANAccount3)
      AccountHoldersPage.addOrganisationAccountHolder("CRS", TestData.iBANAccount)

      When("They open the account holders page")
      AccountHolderAccountPage.clickAccountHolders()
      AccountHoldersPage.checkPage()

      Then("They see the missing account holder text listing the second and third accounts")
      AccountHoldersPage.checkHeading(1)
      AccountHoldersPage.checkAccountsMissing(Seq(TestData.iBANAccount2, TestData.iBANAccount3))
    }

  }
}
