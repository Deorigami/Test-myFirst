import SwiftUI
import ComposeApp

@main
struct ComposeApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView()
                .ignoresSafeArea(.all)
                .onOpenURL { url in
                    RinkuIos.shared.onDeepLinkReceived(url: url.absoluteString)
                }
        }
    }
}

struct ContentView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        return MainKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        // Updates will be handled by Compose
    }
}
