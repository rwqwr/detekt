package io.gitlab.arturbosch.detekt.formatting

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.formatting.wrappers.Indentation
import io.gitlab.arturbosch.detekt.test.TestConfig
import io.gitlab.arturbosch.detekt.test.assert
import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class IndentationSpec : Spek({

    val subject by memoized { Indentation(Config.empty) }

    describe("Indentation rule") {

        describe("indentation level equals 1") {

            val code = "fun main() {\n println()\n}"

            describe("indentation level config of default") {

                it("reports wrong indentation level") {
                    assertThat(subject.lint(code)).hasSize(1)
                }

                it("places finding location to the indentation") {
                    subject.lint(code).assert()
                        .hasSourceLocation(2, 1)
                        .hasTextLocations(13 to 14)
                }
            }

            it("does not report when using an indentation level config of 1") {
                val config = TestConfig("indentSize" to "1")
                assertThat(Indentation(config).lint(code)).isEmpty()
            }
        }

        it("does not report correct indentation level") {
            val code = "fun main() {\n    println()\n}"
            assertThat(subject.lint(code)).isEmpty()
        }
    }
})
