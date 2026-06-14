import Foundation
import AVFoundation

class OfflineManager: ObservableObject {
    static let shared = OfflineManager()
    
    private let fileManager = FileManager.default
    private var downloadsDirectory: URL {
        fileManager.urls(for: .documentDirectory, in: .userDomainMask)[0].appendingPathComponent("Downloads")
    }
    
    @Published var downloadedTracks: [DownloadedTrack] = []
    
    init() {
        createDownloadsDirectory()
        loadDownloadedTracks()
    }
    
    private func createDownloadsDirectory() {
        if !fileManager.fileExists(atPath: downloadsDirectory.path) {
            try? fileManager.createDirectory(at: downloadsDirectory, withIntermediateDirectories: true)
        }
    }
    
    func downloadTrack(trackId: String, url: URL, title: String) {
        let destinationURL = downloadsDirectory.appendingPathComponent("\(trackId).mp3")
        
        URLSession.shared.downloadTask(with: url) { [weak self] tempURL, response, error in
            guard let tempURL = tempURL, error == nil else { return }
            
            try? self?.fileManager.moveItem(at: tempURL, to: destinationURL)
            
            DispatchQueue.main.async {
                let downloadedTrack = DownloadedTrack(
                    id: trackId,
                    title: title,
                    fileURL: destinationURL,
                    downloadDate: Date()
                )
                self?.downloadedTracks.append(downloadedTrack)
                self?.saveDownloadedTracks()
            }
        }.resume()
    }
    
    func deleteDownload(trackId: String) {
        let fileURL = downloadsDirectory.appendingPathComponent("\(trackId).mp3")
        try? fileManager.removeItem(at: fileURL)
        
        downloadedTracks.removeAll { $0.id == trackId }
        saveDownloadedTracks()
    }
    
    func getLocalFileURL(for trackId: String) -> URL? {
        let fileURL = downloadsDirectory.appendingPathComponent("\(trackId).mp3")
        return fileManager.fileExists(atPath: fileURL.path) ? fileURL : nil
    }
    
    private func saveDownloadedTracks() {
        let encoder = JSONEncoder()
        if let data = try? encoder.encode(downloadedTracks) {
            UserDefaults.standard.set(data, forKey: "downloadedTracks")
        }
    }
    
    private func loadDownloadedTracks() {
        guard let data = UserDefaults.standard.data(forKey: "downloadedTracks") else { return }
        let decoder = JSONDecoder()
        downloadedTracks = (try? decoder.decode([DownloadedTrack].self, from: data)) ?? []
    }
}

struct DownloadedTrack: Codable {
    let id: String
    let title: String
    let fileURL: URL
    let downloadDate: Date
}
