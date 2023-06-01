package io.eleven19.snappy

import fs2.io.file.Path

class VerifierSettingsSuite extends munit.CatsEffectSuite {
  test("It should be possible to use \"get\" to get default VerifierSettings") {
    val actual = VerifierSettings.get
    val names = actual.snapshotFileRoot.names
    assert(actual.snapshotFileRoot.endsWith("snappy/core/test/resources/snapshots"))
  }
}
