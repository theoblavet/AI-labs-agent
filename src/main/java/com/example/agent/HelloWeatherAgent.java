/*
 * Copyright 2025 Google LLC
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
package com.example.agent;

import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import com.google.adk.agents.RunConfig;
import com.google.adk.events.Event;
import com.google.adk.runner.InMemoryRunner;
import com.google.adk.sessions.Session;
import com.google.adk.tools.Annotations.Schema;
import com.google.adk.tools.FunctionTool;
import com.google.adk.web.AdkWebServer;
import com.google.genai.types.Content;
import com.google.genai.types.Part;
import io.reactivex.rxjava3.core.Flowable;

import java.util.Map;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HelloWeatherAgent {
    public static BaseAgent ROOT_AGENT = initAgent();

    private static BaseAgent initAgent() {
        return LlmAgent.builder()
            .name("hello-weather-agent")
            .description("Hello World")
            .instruction("""
                You are a friendly assistant, answering questions in a concise manner.
                
                When asked about the weather forecast, you MUST use the `getWeather` function.
                """)
            .model("gemini-2.5-flash")
            .tools(FunctionTool.create(HelloWeatherAgent.class, "getWeather"))
            .build();
    }

    @Schema(description = "Get the weather forecast for a given city")
    public static Map<String, String> getWeather(
        @Schema(name = "city", description = "Name of the city to get the weather forecast for") String city) {
        return Map.of(
            "city", city,
            "forecast", "Sunny day, clear blue sky, temperature up to 24°C"
        );
    }

    public static void main(String[] args) {
        // Run your agent with the ADK Dev UI

//        AdkWebServer.start(ROOT_AGENT);

        // Run your agent from the command-line
        // with your own run event loop

        RunConfig runConfig = RunConfig.builder().build();
        InMemoryRunner runner = new InMemoryRunner(ROOT_AGENT);

        Session session = runner
            .sessionService()
            .createSession(runner.appName(), "user1234")
            .blockingGet();

        try (Scanner scanner = new Scanner(System.in, UTF_8)) {
            while (true) {
                System.out.print("\nYou > ");
                String userInput = scanner.nextLine();
                if ("quit".equalsIgnoreCase(userInput)) {
                    break;
                }

                Content userMsg = Content.fromParts(Part.fromText(userInput));
                Flowable<Event> events = runner.runAsync(session.userId(), session.id(), userMsg, runConfig);

                System.out.print("\nAgent > ");
                events.blockingForEach(event -> {
                    if (event.finalResponse()) {
                        System.out.println(event.stringifyContent());
                    }
                });
            }
        }
    }
}
