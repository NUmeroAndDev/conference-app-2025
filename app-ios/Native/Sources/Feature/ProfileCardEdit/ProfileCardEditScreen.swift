import Component
import Model
import Presentation
import SwiftUI
import Theme

public struct ProfileCardEditScreen: View {
    @State private var presenter = ProfileCardEditPresenter(profile: ProfileProvider())
    @Environment(\.dismiss) private var dismiss

    public init() {}

    public var body: some View {
        ScrollView {
            ProfileCardEditForm(presenter: presenter)
            .padding(.bottom, 80)
        }
        .background(AssetColors.surface.swiftUIColor)
        .navigationTitle("Edit Profile Card")
        .navigationBarTitleDisplayMode(.large)
        .navigationBarBackButtonHidden(presenter.profile.profile == nil)
        .onAppear {
            presenter.profile.subscribeProfileIfNeeded()
        }
        .onChange(of: presenter.profile.isLoading) { _, isLoading in
            // Load form data when profile loading is complete
            if !isLoading {
                presenter.loadForEditing()
            }
        }
        .onChange(of: presenter.isEditing) { _, isEditing in
            // Close the screen when editing is complete
            if !isEditing && presenter.profile.profile != nil {
                dismiss()
            }
        }
    }
}
