package com.nfsindustries.vsd

const val LOG_TAG = "VSD"

const val REQUEST_PERMISSION = 200

const val ONGOING_NOTIFICATION_ID = 23

const val NOTIF_CHANNEL_ID = "VSD_NOTIF_CHANNEL"

const val STRESS_LOWER_LIMIT = 8.0
const val STRESS_UPPER_LIMIT = 13.0

const val MARGINAL_STRESS_LOWER_LIMIT = 7.0
const val MARGINAL_STRESS_UPPER_LIMIT = 12.0

const val STRESS_STRING = "Stress"
const val NO_STRESS_STRING = "No Stress"
const val MARGINAL_STRESS_STRING = "Marginal Stress"

/*
This is really hacky but it makes unit testing easy without mocking Android Resources
This hack should stay here until there is a better way to unit test it or the number of colours grow up
 */
const val STRESS_COLOR = "#FF0000"
const val NO_STRESS_COLOR = "#00FF00"
const val MARGINAL_STRESS_COLOR = "#FF7F00"