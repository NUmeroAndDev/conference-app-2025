import Foundation
import Model
import Observation
import Presentation
import _PhotosUI_SwiftUI

@Observable
public class FormState {
    public var name: String
    public var occupation: String
    public var urlString: String
    public var image: PhotosPickerItem?
    public var cardVariant: ProfileCardVariant
    public var existingImageData: Data?

    public var nameError: String?
    public var occupationError: String?
    public var urlError: String?
    public var imageError: String?

    public init(
        name: String, occupation: String, urlString: String, image: PhotosPickerItem?, cardVariant: ProfileCardVariant, existingImageData: Data? = nil
    ) {
        self.name = name
        self.occupation = occupation
        self.urlString = urlString
        self.image = image
        self.cardVariant = cardVariant
        self.existingImageData = existingImageData
    }

    public func validate() -> Bool {
        nameError = name.isEmpty ? "Name is required" : nil
        occupationError = occupation.isEmpty ? "Occupation is required" : nil
        urlError = urlString.isEmpty ? "URL is required" : nil
        imageError = (image == nil && existingImageData == nil) ? "Image is required" : nil

        return nameError == nil && occupationError == nil && urlError == nil && imageError == nil
    }

    @MainActor
    public func createProfile() async throws -> Profile {
        let imageData: Data

        if let newImage = image {
            guard let data = try await newImage.loadTransferable(type: Data.self) else {
                fatalError("Failed to load new image data")
            }
            imageData = data
        } else if let existingData = existingImageData {
            imageData = existingData
        } else {
            fatalError("No image data available")
        }

        return Profile(
            name: name,
            occupation: occupation,
            url: URL(string: urlString)!,
            image: imageData,
            cardVariant: cardVariant
        )
    }
}

@MainActor
@Observable
public final class ProfileCardEditPresenter {
    public let profile: ProfileProvider

    public var formState: FormState
    public var isEditing: Bool

    public init(profile: ProfileProvider) {
        self.profile = profile
        self.formState = FormState(name: "", occupation: "", urlString: "", image: nil, cardVariant: .nightPill, existingImageData: nil)
        self.isEditing = false
    }

    public func loadForEditing() {
        formState = FormState(
            name: profile.profile?.name ?? "",
            occupation: profile.profile?.occupation ?? "",
            urlString: profile.profile?.url.absoluteString ?? "",
            image: nil,
            cardVariant: profile.profile?.cardVariant ?? .nightPill,
            existingImageData: profile.profile?.image
        )
    }

    @MainActor
    public func createCard() {
        if !formState.validate() { return }

        Task {
            let profileData = try await formState.createProfile()
            profile.saveProfile(profileData)
            self.isEditing = false
        }
    }

    public func setName(_ name: String) {
        formState.name = name
    }

    public func setOccupation(_ occupation: String) {
        formState.occupation = occupation
    }

    public func setLink(_ linkString: String) {
        formState.urlString = linkString
    }

    public func setImage(_ image: PhotosPickerItem?) {
        formState.image = image
    }

    public func setCardVariant(_ variant: ProfileCardVariant) {
        formState.cardVariant = variant
    }
}
