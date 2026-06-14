import SwiftUI

struct ContentView: View {
    @State private var isPlaying = false
    
    var body: some View {
        ZStack {
            // Animated gradient background
            LinearGradient(
                colors: [
                    Color(red: 0.11, green: 0.11, blue: 0.14),
                    Color(red: 0.04, green: 0.04, blue: 0.06)
                ],
                startPoint: .top,
                endPoint: .bottom
            )
            .ignoresSafeArea()
            
            VStack(spacing: 30) {
                // Album Art
                Circle()
                    .fill(
                        LinearGradient(
                            colors: [.purple, .pink],
                            startPoint: .topLeading,
                            endPoint: .bottomTrailing
                        )
                    )
                    .frame(width: 250, height: 250)
                    .shadow(color: .purple.opacity(0.3), radius: 20)
                    .scaleEffect(isPlaying ? 1.02 : 1.0)
                    .animation(.easeInOut(duration: 1).repeatForever(autoreverses: true), value: isPlaying)
                
                // Track Info
                VStack(spacing: 8) {
                    Text("Midnight Dreams")
                        .font(.title)
                        .fontWeight(.bold)
                        .foregroundColor(.white)
                    
                    Text("Alex Rivers")
                        .font(.body)
                        .foregroundColor(.gray)
                }
                
                // Play Button
                Button(action: {
                    isPlaying.toggle()
                }) {
                    Circle()
                        .fill(
                            LinearGradient(
                                colors: [Color(red: 0.62, green: 0.31, blue: 0.87), Color(red: 1.0, green: 0.4, blue: 0.77)],
                                startPoint: .topLeading,
                                endPoint: .bottomTrailing
                            )
                        )
                        .frame(width: 70, height: 70)
                        .overlay(
                            Image(systemName: isPlaying ? "pause.fill" : "play.fill")
                                .font(.system(size: 30))
                                .foregroundColor(.white)
                        )
                        .shadow(color: .purple.opacity(0.5), radius: 15)
                }
            }
            .padding()
        }
    }
}

#Preview {
    ContentView()
}
