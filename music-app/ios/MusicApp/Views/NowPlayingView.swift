import SwiftUI

struct NowPlayingView: View {
    @State private var isPlaying = false
    @State private var currentTime: Double = 0
    @State private var duration: Double = 225
    @State private var isLiked = false
    @State private var isShuffled = false
    @State private var isRepeating = false
    @State private var rotationAngle: Double = 0
    @State private var showingLyrics = false
    
    let trackTitle = "Midnight Dreams"
    let artistName = "Alex Rivers"
    let albumArt = "album_cover"
    
    var body: some View {
        ZStack {
            // Animated Background
            LinearGradient(
                colors: [
                    Color(red: 0.11, green: 0.11, blue: 0.14),
                    Color(red: 0.04, green: 0.04, blue: 0.06)
                ],
                startPoint: .top,
                endPoint: .bottom
            )
            .ignoresSafeArea()
            .overlay(
                Circle()
                    .fill(Color.purple.opacity(0.1))
                    .blur(radius: 60)
                    .offset(x: -100, y: -200)
            )
            
            VStack(spacing: 24) {
                Spacer()
                
                // Album Art with Rotation Animation
                ZStack {
                    Circle()
                        .fill(
                            LinearGradient(
                                colors: [.purple, .pink],
                                startPoint: .topLeading,
                                endPoint: .bottomTrailing
                            )
                        )
                        .frame(width: 280, height: 280)
                        .shadow(color: .purple.opacity(0.3), radius: 20)
                        .rotationEffect(.degrees(isPlaying ? rotationAngle : 0))
                        .onAppear {
                            withAnimation(.linear(duration: 20).repeatForever(autoreverses: false)) {
                                rotationAngle = 360
                            }
                        }
                    
                    // Wave Visualizer Overlay
                    if isPlaying {
                        WaveVisualizer()
                            .frame(width: 280, height: 280)
                    }
                }
                
                // Track Info
                VStack(spacing: 8) {
                    Text(trackTitle)
                        .font(.title)
                        .fontWeight(.bold)
                        .foregroundColor(.white)
                    
                    Text(artistName)
                        .font(.body)
                        .foregroundColor(.gray)
                }
                
                // Progress Bar
                VStack(spacing: 8) {
                    Slider(value: $currentTime, in: 0...duration, onEditingChanged: { _ in })
                        .accentColor(.purple)
                        .tint(
                            LinearGradient(
                                colors: [Color(red: 0.62, green: 0.31, blue: 0.87), Color(red: 1.0, green: 0.4, blue: 0.77)],
                                startPoint: .leading,
                                endPoint: .trailing
                            )
                        )
                    
                    HStack {
                        Text(formatTime(currentTime))
                            .font(.caption)
                            .foregroundColor(.gray)
                        Spacer()
                        Text(formatTime(duration))
                            .font(.caption)
                            .foregroundColor(.gray)
                    }
                }
                .padding(.horizontal, 20)
                
                // Player Controls
                HStack(spacing: 30) {
                    // Shuffle Button
                    Button(action: { isShuffled.toggle() }) {
                        Image(systemName: "shuffle")
                            .font(.title2)
                            .foregroundColor(isShuffled ? .purple : .white)
                    }
                    
                    // Previous Button
                    Button(action: {}) {
                        Image(systemName: "backward.fill")
                            .font(.title)
                            .foregroundColor(.white)
                    }
                    
                    // Play/Pause Button (Morphing)
                    Button(action: {
                        withAnimation(.spring(response: 0.3, dampingFraction: 0.6)) {
                            isPlaying.toggle()
                        }
                    }) {
                        ZStack {
                            Circle()
                                .fill(
                                    LinearGradient(
                                        colors: [Color(red: 0.62, green: 0.31, blue: 0.87), Color(red: 1.0, green: 0.4, blue: 0.77)],
                                        startPoint: .topLeading,
                                        endPoint: .bottomTrailing
                                    )
                                )
                                .frame(width: 70, height: 70)
                                .shadow(color: .purple.opacity(0.5), radius: 15)
                            
                            Image(systemName: isPlaying ? "pause.fill" : "play.fill")
                                .font(.title)
                                .foregroundColor(.white)
                                .transition(.scale.combined(with: .opacity))
                        }
                    }
                    
                    // Next Button
                    Button(action: {}) {
                        Image(systemName: "forward.fill")
                            .font(.title)
                            .foregroundColor(.white)
                    }
                    
                    // Repeat Button
                    Button(action: { isRepeating.toggle() }) {
                        Image(systemName: isRepeating ? "repeat.1" : "repeat")
                            .font(.title2)
                            .foregroundColor(isRepeating ? .purple : .white)
                    }
                }
                .padding(.horizontal, 20)
                
                // Action Buttons
                HStack(spacing: 40) {
                    Button(action: { isLiked.toggle() }) {
                        Image(systemName: isLiked ? "heart.fill" : "heart")
                            .font(.title2)
                            .foregroundColor(isLiked ? .pink : .white)
                    }
                    
                    Button(action: { showingLyrics.toggle() }) {
                        Image(systemName: "text.bubble")
                            .font(.title2)
                            .foregroundColor(.white)
                    }
                    
                    Button(action: {}) {
                        Image(systemName: "square.and.arrow.up")
                            .font(.title2)
                            .foregroundColor(.white)
                    }
                    
                    Button(action: {}) {
                        Image(systemName: "list.bullet")
                            .font(.title2)
                            .foregroundColor(.white)
                    }
                }
                
                Spacer()
            }
            .padding()
        }
        .sheet(isPresented: $showingLyrics) {
            LyricsView()
        }
    }
    
    private func formatTime(_ time: Double) -> String {
        let minutes = Int(time) / 60
        let seconds = Int(time) % 60
        return String(format: "%d:%02d", minutes, seconds)
    }
}

struct WaveVisualizer: View {
    @State private var animating = false
    
    var body: some View {
        HStack(spacing: 4) {
            ForEach(0..<20) { index in
                RoundedRectangle(cornerRadius: 2)
                    .fill(Color.white.opacity(0.5))
                    .frame(width: 3, height: animating ? CGFloat.random(in: 10...60) : 20)
                    .animation(
                        Animation.easeInOut(duration: 0.5)
                            .repeatForever()
                            .delay(Double(index) * 0.05),
                        value: animating
                    )
            }
        }
        .onAppear { animating = true }
        .onDisappear { animating = false }
    }
}

struct LyricsView: View {
    @Environment(\.dismiss) var dismiss
    
    var body: some View {
        ZStack {
            Color(red: 0.04, green: 0.04, blue: 0.06).ignoresSafeArea()
            
            VStack {
                HStack {
                    Text("Lyrics")
                        .font(.title)
                        .foregroundColor(.white)
                    Spacer()
                    Button("Close") { dismiss() }
                        .foregroundColor(.purple)
                }
                .padding()
                
                ScrollView {
                    VStack(alignment: .leading, spacing: 16) {
                        Text("[Verse 1]")
                            .foregroundColor(.purple)
                        Text("In the midnight hour\nI feel the power\nOf dreams that take me higher")
                            .foregroundColor(.white)
                        
                        Text("[Chorus]")
                            .foregroundColor(.purple)
                        Text("And I'm never coming down\nLiving in the sound\nOf midnight dreams")
                            .foregroundColor(.white)
                    }
                    .padding()
                }
            }
        }
    }
}

#Preview {
    NowPlayingView()
}
