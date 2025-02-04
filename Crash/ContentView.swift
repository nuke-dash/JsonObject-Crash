//
//  ContentView.swift
//  Crash
//
//  Created by Thomas Panis on 04/02/2025.
//

import SwiftUI
import AppSharedLogicMultiPlatform

struct ContentView: View {
    var body: some View {
        VStack {
            Image(systemName: "globe")
                .imageScale(.large)
                .foregroundStyle(.tint)
            Text("Hello, world!")
        }
        .padding()
        .onAppear {
            Task {
                let container = Container()
                let eccConfigRetriever = container.eccConfigRetriever
                let catalog = try? await eccConfigRetriever.config()
                print(String(describing: catalog?.alternatives.first?.asServer()?.queryDefinitionAsString))
                print(String(describing: catalog?.alternatives.first?.asServer()?.queryDefinition))
            }
        }
    }
}

#Preview {
    ContentView()
}
