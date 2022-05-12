package com.itrocket.union.authContainer.domain

import com.itrocket.union.authContainer.domain.dependencies.AuthContainerRepository
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.authContainer.domain.entity.AuthContainerStep

class AuthContainerInteractor(
    private val containerRepository: AuthContainerRepository,
    private val coreDispatchers: CoreDispatchers
) {

    fun calculateNextStep(currentStep: AuthContainerStep): AuthContainerStep {
        return if (currentStep.ordinal == AuthContainerStep.values().lastIndex) {
            currentStep
        } else {
            AuthContainerStep.values()[currentStep.ordinal + 1]
        }
    }

    fun calculatePrevStep(currentStep: AuthContainerStep): AuthContainerStep {
        return if (currentStep.ordinal == 0) {
            currentStep
        } else {
            AuthContainerStep.values()[currentStep.ordinal - 1]
        }
    }
}