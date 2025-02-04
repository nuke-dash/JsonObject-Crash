package com.company.app.sharedlogic

import com.company.app.sharedlogic.ecc.ECCConfigRetriever
import com.company.app.sharedlogic.ecc.ECCConfigRetrieverImplementation

class Container() {
    val eccConfigRetriever: ECCConfigRetriever by lazy {
        ECCConfigRetrieverImplementation()
    }
}
