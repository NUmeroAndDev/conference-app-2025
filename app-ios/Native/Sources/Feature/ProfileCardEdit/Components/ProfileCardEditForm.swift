import Component
import Model
import SwiftUI
import Theme

// TODO: add varidation
public struct ProfileCardEditForm: View {
    let presenter: ProfileCardEditPresenter

    public init(presenter: ProfileCardEditPresenter) {
        self.presenter = presenter
    }

    public var body: some View {
        VStack(spacing: 32) {
            Text(
                String(
                    localized: "Let's create a profile card to introduce yourself at events or on social media!",
                    bundle: .module)
            )
            .typography(Typography.bodyLarge)
            .foregroundStyle(AssetColors.onSurfaceVariant.swiftUIColor)
            .multilineTextAlignment(.leading)

            ProfileCardInputTextField(
                title: String(localized: "Nickname", bundle: .module),
                text: .init(
                    get: {
                        presenter.formState.name
                    },
                    set: {
                        presenter.setName($0)
                    }
                ),
            )

            ProfileCardInputTextField(
                title: String(localized: "Occupation", bundle: .module),
                text: .init(
                    get: {
                        presenter.formState.occupation
                    },
                    set: {
                        presenter.setOccupation($0)
                    }
                ),
            )

            ProfileCardInputTextField(
                title: String(localized: "Link（ex.X、Instagram...）", bundle: .module),
                placeholder: "https://",
                text: .init(
                    get: {
                        presenter.formState.urlString
                    },
                    set: {
                        presenter.setLink($0)
                    }
                )
            )

            ProfileCardEditInputImageWrapper(presenter: presenter)

            ProfileCardInputCardVariant(
                selectedCardType: .init(
                    get: {
                        presenter.formState.cardVariant
                    },
                    set: {
                        presenter.setCardVariant($0)
                    }
                )
            )

            Button {
                presenter.createCard()
            } label: {
                let isEditing = presenter.profile.profile != nil
                let buttonText = isEditing ?
                    String(localized: "Save Card", bundle: .module) :
                    String(localized: "Create Card", bundle: .module)
                Text(buttonText)
                    .frame(maxWidth: .infinity)
            }
            .filledButtonStyle()
        }
        .padding(.horizontal, 16)
    }
}

private struct ProfileCardEditInputImageWrapper: View {
    let presenter: ProfileCardEditPresenter
    @State private var currentInitialImage: UIImage?
    
    var body: some View {
        ProfileCardEditInputImage(
            selectedPhoto: .init(
                get: { presenter.formState.image },
                set: { presenter.setImage($0) }
            ),
            initialImage: currentInitialImage,
            title: String(localized: "Image", bundle: .module)
        )
        .onChange(of: presenter.formState.existingImageData) { _, newData in
            if let newData = newData, let uiImage = UIImage(data: newData) {
                currentInitialImage = uiImage
            } else {
                currentInitialImage = nil
            }
        }
        .onAppear {
            if let imageData = presenter.formState.existingImageData,
               let uiImage = UIImage(data: imageData) {
                currentInitialImage = uiImage
            }
        }
    }
}
