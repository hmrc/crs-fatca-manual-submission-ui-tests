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
import uk.gov.hmrc.ui.pages.manual.controllingpersonandsubstantialowners.*
import uk.gov.hmrc.ui.specs.BaseSpec
import uk.gov.hmrc.ui.specs.tags.*

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

      // TODO: Replace direct navigation below with the click-through once
      // /manual/cp-so/account-holder is implemented.
      When("They navigate directly to the individual or organisation page")
      CpSoIndividualOrOrganisationPage.goToPage()

      //      When("They open the cp-so account holder task")
      //      ManualSendAReportIndexPage.clickCpSoAccountHolder()

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

      // TODO: Replace direct navigation below with the click-through once
      // /manual/cp-so/account-holder is implemented.
      When("They navigate directly to the individual or organisation page")
      CpSoIndividualOrOrganisationPage.goToPage()

      //      When("They open the cp-so account holder task")
      //      ManualSendAReportIndexPage.clickCpSoAccountHolder()

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

      // TODO: Replace direct navigation below once the CRS route into this
      // page (via /manual/cp-so/account-holder) is implemented.
      When("They navigate directly to the individual name page")
      CpSoIndividualNamePage.goToPage()

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

      When("They navigate directly to the individual or organisation page")
      CpSoIndividualOrOrganisationPage.goToPage()

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

      // TODO: Replace direct navigation below once the CRS route into this
      // page (via /manual/cp-so/account-holder) is implemented.
      When("They navigate directly to the individual or organisation page")
      CpSoIndividualOrOrganisationPage.goToPage()

      Then("They see the page not found error")
      PageNotFoundPage.checkPage()
    }

  }
}
