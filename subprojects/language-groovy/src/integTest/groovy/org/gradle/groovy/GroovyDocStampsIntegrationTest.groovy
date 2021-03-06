/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.groovy

import org.gradle.integtests.fixtures.MultiVersionIntegrationSpec
import org.gradle.integtests.fixtures.TargetCoverage
import org.gradle.testing.fixture.GroovyCoverage

@TargetCoverage({GroovyCoverage.SUPPORTS_TIMESTAMP})
class GroovyDocStampsIntegrationTest extends MultiVersionIntegrationSpec {

    def "time and version stamp switches work"() {
        when:
        buildFile << """
            apply plugin: "groovy"

            ${mavenCentralRepository()}

            dependencies {
                implementation "org.codehaus.groovy:groovy:${version}"
            }

            // coverage for false case(s) handled by GroovyDocIntegrationTest
            groovydoc {
              noTimestamp = true
              noVersionStamp = true
            }
        """

        file("src/main/groovy/pkg/Thing.groovy") << """
            package pkg

            class Thing {}
        """

        then:
        succeeds "groovydoc"

        and:
        def text = file('build/docs/groovydoc/pkg/Thing.html').text
        !(text =~ /Generated by groovydoc \((.+?)\)/)
        !(text =~ /Generated by groovydoc .* on /)
    }

}
