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

class AccountHolderManualSpec extends BaseSpec with ManualJourneyHelper {

  Feature("CRS/FATCA Manual - Account Holder journey") {

    Scenario(
      "Account Holder - selecting Individual continues the journey",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as CRS")
      navigateToTaskList("CRS")

      // TODO: Replace direct navigation below with the click-through once
      // ManualSendAReportIndexPage.clickAccountHolder() is implemented.
      When("They navigate directly to the individual or organisation page")
      AccountHolderIndividualOrOrganisationPage.goToPage()

      //      When("They open the account holder task")
      //      ManualSendAReportIndexPage.clickAccountHolder()

      Then("They are on the individual or organisation page")
      AccountHolderIndividualOrOrganisationPage.checkPage()

      When("They select Individual and continue")
      AccountHolderIndividualOrOrganisationPage.selectIndividualAndContinue()
    }

    Scenario(
      "Account Holder - selecting Organisation continues the journey",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as FATCA")
      navigateToTaskList("FATCA")

      // TODO: Replace direct navigation below with the click-through once
      // ManualSendAReportIndexPage.clickAccountHolder() is implemented.
      When("They navigate directly to the individual or organisation page")
      AccountHolderIndividualOrOrganisationPage.goToPage()

      //      When("They open the account holder task")
      //      ManualSendAReportIndexPage.clickAccountHolder()

      Then("They are on the individual or organisation page")
      AccountHolderIndividualOrOrganisationPage.checkPage()

      When("They select Organisation and continue")
      AccountHolderIndividualOrOrganisationPage.selectOrganisationAndContinue()
    }
  }
}
